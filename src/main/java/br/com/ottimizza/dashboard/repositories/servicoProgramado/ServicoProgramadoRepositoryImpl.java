package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.constraints.Agrupamento;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoStatus;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoAgrupado;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
import br.com.ottimizza.dashboard.models.departamentos.QDepartamento;
import br.com.ottimizza.dashboard.models.empresas.EmpresaShort;
import br.com.ottimizza.dashboard.models.empresas.QEmpresaShort;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoAgrupado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.servicos.ServicoShort;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ServicoProgramadoRepositoryImpl implements ServicoProgramadoRepositoryCustom {
    
    @PersistenceContext
    EntityManager em;
    
    private QServicoProgramado servicoProgramado = QServicoProgramado.servicoProgramado;
    private QUsuario usuario = QUsuario.usuario;
    private QDepartamento departamento = QDepartamento.departamento;
    private QEmpresaShort empresa = QEmpresaShort.empresaShort;
    private QServico servico = QServico.servico;
    
    private List<Long> departamentosId = new ArrayList<>();
    private List<Long> empresasId = new ArrayList<>();
    private List<Long> servicosId = new ArrayList<>();
    private List<Long> usuariosId = new ArrayList<>();
    
    @Override
    public Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro) {
        try {    
        
        JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id)) //JOIN SERVIÇO
                .innerJoin(empresa).on(servicoProgramado.cliente.id.eq(empresa.id)) //JOIN EMPRESA
                .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id)) //JOIN USUÁRIO
                .innerJoin(departamento).on(usuario.departamento.id.eq(departamento.id)); //JOIN DEPARTAMENTO

            /*** FILTRO SERVIÇOS PROGRAMADOS ***/

                //--STATUS
                if(filtro.getSituacao() != null){

                    //---ABERTO
                    if(filtro.getSituacao() == ServicoProgramadoSituacao.ABERTO) {
                        query.where(servicoProgramado.status.in(ServicoProgramadoStatus.NAO_INICIADO,ServicoProgramadoStatus.INICIADO));
                        
                        if(filtro.getPrazo() != null){
                            Date dataAtual = new Date();
                            BooleanBuilder prazos = new BooleanBuilder();
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.NO_PRAZO))
                                prazos.or(servicoProgramado.dataProgramadaEntrega.goe(dataAtual));
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.ATRASADO))
                                prazos.or(servicoProgramado.dataProgramadaEntrega.lt(dataAtual).and(servicoProgramado.dataVencimento.goe(dataAtual)));
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.VENCIDO))
                                prazos.or(servicoProgramado.dataVencimento.lt(dataAtual));
                            
                            query.where(prazos);
                        }
                    }

                    //---ENCERRADO
                    if(filtro.getSituacao() == ServicoProgramadoSituacao.ENCERRADO){
                        query.where(servicoProgramado.status.in(ServicoProgramadoStatus.CONCLUIDO,ServicoProgramadoStatus.ENVIADO));

                        if(filtro.getPrazo() != null){
                            BooleanBuilder prazos = new BooleanBuilder();
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.NO_PRAZO))
                                prazos.or(servicoProgramado.dataProgramadaEntrega.goe(servicoProgramado.dataTermino));
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.ATRASADO))
                                prazos.or(servicoProgramado.dataProgramadaEntrega.lt(servicoProgramado.dataTermino).and(servicoProgramado.dataVencimento.goe(servicoProgramado.dataTermino)));
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.VENCIDO))
                                prazos.or(servicoProgramado.dataVencimento.lt(servicoProgramado.dataTermino));
                            
                            query.where(prazos);
                        } 
                    }
                }

                //--DEPARTAMENTO
                if(filtro.getDepartamento() != null){
                    for (DepartamentoShort departamentoShort : filtro.getDepartamento()) {
                        departamentosId.add(departamentoShort.getId());
                    }
                    
                    query.where(departamento.id.in(departamentosId));
                } 

                //--USUARIO
                if(filtro.getUsuario() != null){
                    for (Usuario usuario : filtro.getUsuario()) {
                        usuariosId.add(usuario.getId());
                    }
                    
                    query.where(usuario.id.in(usuariosId));
                } 

                //--SERVIÇO
                if(filtro.getServico() != null){
                    for (ServicoShort servicoShort : filtro.getServico()) {
                        servicosId.add(servicoShort.getId());
                    }
                    
                    query.where(servico.id.in(servicosId));
                } 

                //--EMPRESA
                if(filtro.getEmpresa() != null){
                    for (EmpresaShort empresaShort : filtro.getEmpresa()) {
                        empresasId.add(empresaShort.getId());
                    }
                    
                    query.where(empresa.id.in(empresasId));
                } 

                //DATA PROGRAMADA
                if(filtro.getDataProgramadaInicio() != null && filtro.getDataProgramadaTermino() != null){
                    query.where(servicoProgramado.dataProgramadaEntrega.goe(filtro.getDataProgramadaInicio())
                        .and(servicoProgramado.dataProgramadaEntrega.loe(filtro.getDataProgramadaTermino())));
                }
            /*** FIM FILTRO SERVIÇOS PROGRAMADOS ***/

            return query.fetchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Long(-1);
        }
    }

    @Override
    public List contadorServicoProgramadoGroupBy(Short agrupamento, ServicoProgramadoFiltroAvancado filtro) {
        NumberPath<Long> aliasContagem = Expressions.numberPath(Long.class, "contagem");
        JPAQuery query = new JPAQuery(em);
        
            if(agrupamento == Agrupamento.SERVICO){
                query.select(Projections.constructor(ServicoAgrupado.class, servico.nome, servicoProgramado.count().as(aliasContagem)))
                .from(servicoProgramado)    
                .innerJoin(servicoProgramado.servico, servico)
                .innerJoin(servicoProgramado.alocadoPara, usuario); //UTILIZADO O DEPARTAMENTO ALOCADO DO USUÁRIO PARA FILTRO
                
                //*** FILTRO SERVIÇO ***
                
                    //--DEPARTAMENTO
                    if(filtro.getDepartamento() != null){
                        for (DepartamentoShort departamentoShort : filtro.getDepartamento()){
                            departamentosId.add(departamentoShort.getId());
                        }  
                        query.where(usuario.departamento.id.in(departamentosId));
                    } 
                
                //*** FIM FILTRO SERVIÇO ***
                
                query.groupBy(servico.id).orderBy(aliasContagem.desc());
            }
            
            if(agrupamento == Agrupamento.DEPARTAMENTO){
                query.select(Projections.constructor(DepartamentoAgrupado.class, departamento.descricao, servicoProgramado.count().as(aliasContagem)))
                .from(servicoProgramado)    
                .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id))
                .innerJoin(departamento).on(usuario.departamento.id.eq(departamento.id));
                
                query.groupBy(departamento.id).orderBy(aliasContagem.desc());
            }
            
        return query.fetch();
    }
    
}
