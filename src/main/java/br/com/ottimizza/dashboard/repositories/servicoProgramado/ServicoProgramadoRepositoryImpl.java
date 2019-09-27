package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.constraints.Agrupamento;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoStatus;
import br.com.ottimizza.dashboard.models.caracteristica.caracteristica_empresas.QCaracteristicaEmpresa;
import br.com.ottimizza.dashboard.models.categoria.QCategoria;
import br.com.ottimizza.dashboard.models.categoria.QCategoriaServico;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoAgrupado;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
import br.com.ottimizza.dashboard.models.departamentos.QDepartamento;
import br.com.ottimizza.dashboard.models.empresas.EmpresaResponsavelDataVencimento;
import br.com.ottimizza.dashboard.models.empresas.EmpresaShort;
import br.com.ottimizza.dashboard.models.empresas.QEmpresaShort;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoAgrupado;
import br.com.ottimizza.dashboard.models.servicos.ServicoAgrupadoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancadoDataProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoShort;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
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
    private QCategoriaServico categoriaServico = QCategoriaServico.categoriaServico;
    private QCaracteristicaEmpresa caracteristicaEmpresa = QCaracteristicaEmpresa.caracteristicaEmpresa;
    
    @Override
    public Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro, Usuario autenticado) {
        List<Long> departamentosId = new ArrayList<>();
        List<Long> empresasId = new ArrayList<>();
        List<Long> servicosId = new ArrayList<>();
        List<Long> usuariosId = new ArrayList<>();
        try {    
            
        if(autenticado == null) return 0L;
        
        JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id)) //JOIN SERVIÇO
                .innerJoin(empresa).on(servicoProgramado.cliente.id.eq(empresa.id)) //JOIN EMPRESA
                .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id)) //JOIN USUÁRIO
                .innerJoin(departamento).on(departamento.id.eq( //JOIN DEPARTAMENTO
                    new CaseBuilder.Initial(usuario.departamento.id.isNull()).then(servico.grupoServico.id)
                        .otherwise(usuario.departamento.id))
                );
            
            /*** FILTRO SERVIÇOS PROGRAMADOS ***/
            
                //CONTABILIDADE
                query.where(servico.contabilidade.id.eq(autenticado.getContabilidade().getId()));

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
                    if(!filtro.getDepartamento().isEmpty()){
                        for (DepartamentoShort departamentoShort : filtro.getDepartamento()) {
                            departamentosId.add(departamentoShort.getId());
                        }

                        query.where(departamento.id.in(departamentosId));
                    }
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
                    if(!filtro.getServico().isEmpty()){
                        for (ServicoShort servicoShort : filtro.getServico()) {
                            servicosId.add(servicoShort.getId());
                        }

                        query.where(servico.id.in(servicosId));
                    }
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
                
                //CATEGORIA 
                if(filtro.getCategoria() != null){
                    if(filtro.getCategoria().getId() != null){
                        query.innerJoin(categoriaServico)
                            .on(servico.id.eq(categoriaServico.servico.id)
                                .and(categoriaServico.categoria.id.eq(filtro.getCategoria().getId())));
                    }
                }
                
                //CARACTERISTICA
                if(filtro.getCaracteristica()!= null){
                    if(filtro.getCaracteristica().getId() != null){
                        query.innerJoin(caracteristicaEmpresa)
                            .on(empresa.id.eq(caracteristicaEmpresa.empresa.id)
                                .and(caracteristicaEmpresa.caracteristica.id.eq(filtro.getCaracteristica().getId())));
                    }
                }
                
            /*** FIM FILTRO SERVIÇOS PROGRAMADOS ***/

            return query.fetchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Long(-1);
        }
    }

    @Override
    public List contadorServicoProgramadoGroupBy(Short agrupamento, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado) {
        List<Long> departamentosId = new ArrayList<>();
        List<Long> servicosId = new ArrayList<>();
        if(autenticado == null) return null;
        try {
            
            NumberPath<Long> aliasContagem = Expressions.numberPath(Long.class, "contagem");
            JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id))
                .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id))
                .innerJoin(departamento).on(departamento.id.eq(
                    new CaseBuilder.Initial(usuario.departamento.id.isNull()).then(servico.grupoServico.id)
                        .otherwise(usuario.departamento.id))
                );

                //###SERVIÇO COM VENCIMENTO###
                if(agrupamento == Agrupamento.SERVICOVENCIMENTO){
                    
                    query.select(Projections.constructor(ServicoAgrupadoProgramado.class, servico.id, servico.nome, servicoProgramado.dataProgramadaEntrega, servicoProgramado.count().as(aliasContagem)));
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
                    if(filtro.getDepartamento() != null ){
                        if(!filtro.getDepartamento().isEmpty()){
                            for (DepartamentoShort departamentoShort : filtro.getDepartamento()){
                                if(departamentoShort.getId() != null) departamentosId.add(departamentoShort.getId());
                            }
                            if(!departamentosId.isEmpty()) query.where(departamento.id.in(departamentosId));
                        }
                    }
                    
                    //--SERVIÇO
                    if(filtro.getServico() != null){
                        if(!filtro.getServico().isEmpty()){
                            for (ServicoShort servicoShort : filtro.getServico()) {
                                servicosId.add(servicoShort.getId());
                            }
                            query.where(servico.id.in(servicosId));
                        }
                    }
                    
                    query.groupBy(servicoProgramado.dataProgramadaEntrega,servico.nome,servico.id).orderBy(servicoProgramado.dataProgramadaEntrega.asc(),servico.nome.asc());
                    
                }
                
                //###SERVIÇO###
                if(agrupamento == Agrupamento.SERVICO){
                    query.select(Projections.constructor(ServicoAgrupado.class, servico.id, servico.nome, servicoProgramado.count().as(aliasContagem)));

                    //*** FILTRO SERVIÇO ***
                        //--DEPARTAMENTO
                        if(filtro.getDepartamento() != null){
                            if(!filtro.getDepartamento().isEmpty()){
                                for (DepartamentoShort departamentoShort : filtro.getDepartamento()){
                                    departamentosId.add(departamentoShort.getId());
                                }  
                                query.where(departamento.id.in(departamentosId));
                            }
                        }
                        
                        //--CATEGORIA
                        if(filtro.getCategoria() != null){
                            if(filtro.getCategoria().getId() != null){
                                query.innerJoin(categoriaServico)
                                    .on(servico.id.eq(categoriaServico.servico.id)
                                        .and(categoriaServico.categoria.id.eq(filtro.getCategoria().getId())));
                            }
                        }
                    //*** FIM FILTRO SERVIÇO ***

                    query.groupBy(servico.id).orderBy(aliasContagem.desc());
                }

                //###DEPARTAMENTO###
                if(agrupamento == Agrupamento.DEPARTAMENTO){
                    query.select(Projections.constructor(DepartamentoAgrupado.class, departamento.id, departamento.descricao, servicoProgramado.count().as(aliasContagem)));
                    query.groupBy(departamento.id).orderBy(aliasContagem.desc());
                }
                
                //CATEGORIA 
                if(filtro.getCategoria() != null){
                    if(filtro.getCategoria().getId() != null){
                        query.innerJoin(categoriaServico)
                            .on(servico.id.eq(categoriaServico.servico.id)
                                .and(categoriaServico.categoria.id.eq(filtro.getCategoria().getId())));
                    }
                }
                
                //CARACTERISTICA
                if(filtro.getCaracteristica()!= null){
                    if(filtro.getCaracteristica().getId() != null){
                        query
                            .innerJoin(empresa).on(servicoProgramado.cliente.id.eq(empresa.id)) //EMPRESA
                            .innerJoin(caracteristicaEmpresa).on(empresa.id.eq(caracteristicaEmpresa.empresa.id)//EMPRESA-CARACTERISTICA
                                .and(caracteristicaEmpresa.caracteristica.id.eq(filtro.getCaracteristica().getId())));
                    }
                }

                //CONTABILIDADE
                query.where(servico.contabilidade.id.eq(autenticado.getContabilidade().getId()));

                //DATA PROGRAMADA
                if(filtro.getDataProgramadaInicio() != null && filtro.getDataProgramadaTermino() != null){
                    query.where(servicoProgramado.dataProgramadaEntrega.goe(filtro.getDataProgramadaInicio())
                        .and(servicoProgramado.dataProgramadaEntrega.loe(filtro.getDataProgramadaTermino())));
                }
            return query.fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<?> listaEmpresaResponsavelDataTermino(Long idServico, Long limit, Long beforeServicoProgramaId, String beforeCodigoErp, ServicoProgramadoFiltroAvancadoDataProgramado info, Usuario autenticado) {
        List<Long> departamentosId = new ArrayList<>();
        if(autenticado == null) return null;
        try {
            ServicoProgramadoFiltroAvancado filtro = info.getFiltro();
            
            JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id))
                .innerJoin(empresa).on(servicoProgramado.cliente.id.eq(empresa.id))
                .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id))
                .innerJoin(departamento).on(departamento.id.eq(
                    new CaseBuilder.Initial(usuario.departamento.id.isNull()).then(servico.grupoServico.id)
                        .otherwise(usuario.departamento.id))
                );
            
            //SELECT
            query.select(Projections.constructor(EmpresaResponsavelDataVencimento.class, usuario.nome, usuario.urlFoto, empresa.codigoErp, empresa.razaoSocial, servicoProgramado.id, servicoProgramado.dataTermino));
            
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
            
            //--DATA PROGRAMADA
            query.where(servicoProgramado.dataProgramadaEntrega.eq(info.getDataProgramada()));
            
            //--SERVIÇO
            query.where(servico.id.eq(idServico));
            
            //--DEPARTAMENTO
            if(filtro.getDepartamento() != null){
                if(!filtro.getDepartamento().isEmpty()){
                    for (DepartamentoShort departamentoShort : filtro.getDepartamento()) {
                        departamentosId.add(departamentoShort.getId());
                    }

                    query.where(departamento.id.in(departamentosId));
                }
            }
            
            //CONTABILIDADE
            query.where(servico.contabilidade.id.eq(autenticado.getContabilidade().getId()));
            
            //ORDER BY
            query.orderBy(empresa.codigoErp.asc(),servicoProgramado.id.asc());
            
            if(limit != null){
                query.limit(limit);
                if(beforeServicoProgramaId != null && beforeCodigoErp != null){
                    BooleanExpression expresion1 = empresa.codigoErp.gt(beforeCodigoErp);
                    BooleanExpression expresion2 = empresa.codigoErp.eq(beforeCodigoErp);
                    BooleanExpression expresion3 = servicoProgramado.id.gt(beforeServicoProgramaId);
                    
                    query.where(expresion1.or(expresion2.and(expresion3)));
                }
            }
            
            return query.fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
