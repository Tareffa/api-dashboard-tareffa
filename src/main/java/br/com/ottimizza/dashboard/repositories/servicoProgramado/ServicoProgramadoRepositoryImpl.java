package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoStatus;
import br.com.ottimizza.dashboard.models.departamentos.QDepartamento;
import br.com.ottimizza.dashboard.models.empresas.QEmpresaShort;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoAgrupado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    private BooleanExpression noPrazoAberto(List<Short> prazo, Date data){
        if(prazo == null && prazo.contains(ServicoProgramadoPrazo.NO_PRAZO)) return null;
        return servicoProgramado.dataProgramadaEntrega.goe(data);
    }
    private BooleanExpression atrasadoAberto(List<Short> prazo, Date data){
        if(prazo == null && prazo.contains(ServicoProgramadoPrazo.ATRASADO)) return null;
        return servicoProgramado.dataProgramadaEntrega.lt(data).and(servicoProgramado.dataVencimento.goe(data));
    }
    private BooleanExpression vencidoAberto(List<Short> prazo, Date data){
        if(prazo == null && prazo.contains(ServicoProgramadoPrazo.VENCIDO)) return null;
        return servicoProgramado.dataVencimento.lt(data);
    }
    
    private BooleanExpression noPrazoEncerrado(List<Short> prazo){
        if(prazo == null && prazo.contains(ServicoProgramadoPrazo.NO_PRAZO)) return null;
        return servicoProgramado.dataProgramadaEntrega.goe(servicoProgramado.dataTermino);
    }
    private BooleanExpression atrasadoEncerrado(List<Short> prazo){
        if(prazo == null && prazo.contains(ServicoProgramadoPrazo.ATRASADO)) return null;
        return servicoProgramado.dataProgramadaEntrega.lt(servicoProgramado.dataTermino).and(servicoProgramado.dataVencimento.goe(servicoProgramado.dataTermino));
    }
    private BooleanExpression vencidoEncerrado(List<Short> prazo){
        if(prazo == null && prazo.contains(ServicoProgramadoPrazo.VENCIDO)) return null;
        return servicoProgramado.dataVencimento.lt(servicoProgramado.dataTermino);
    }
    
    @Override
    public Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro) {

        System.out.println("INICIANDO CONTAGEM");
        try {    
        
        JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id)) //JOIN SERVIÇO
                .innerJoin(empresa).on(servicoProgramado.cliente.id.eq(empresa.id)) //JOIN EMPRESA
                .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id)) //JOIN USUÁRIO
                .innerJoin(departamento).on(usuario.departamento.id.eq(departamento.id)); //JOIN DEPARTAMENTO

            /*** FILTRO SERVIÇOS PROGRAMADOS ***/

                //STATUS
                if(filtro.getSituacao() != null){

                    //ABERTO
                    if(filtro.getSituacao() == ServicoProgramadoSituacao.ABERTO) {
                        query.where(servicoProgramado.status.in(ServicoProgramadoStatus.NAO_INICIADO,ServicoProgramadoStatus.INICIADO));

                        if(filtro.getPrazo() != null){
                            Date dataAtual = new Date();
                            query.where(
                                noPrazoAberto(filtro.getPrazo(), dataAtual)
                                .or(atrasadoAberto(filtro.getPrazo(), dataAtual))
                                .or(vencidoAberto(filtro.getPrazo(), dataAtual)));
                        }
                    }

                    //ENCERRADO
                    if(filtro.getSituacao() == ServicoProgramadoSituacao.ENCERRADO){
                        query.where(servicoProgramado.status.in(ServicoProgramadoStatus.CONCLUIDO,ServicoProgramadoStatus.ENVIADO));

                        if(filtro.getPrazo() != null){
                            query.where(
                                noPrazoEncerrado(filtro.getPrazo())
                                .or(atrasadoEncerrado(filtro.getPrazo()))
                                .or(vencidoEncerrado(filtro.getPrazo())));
                        } 
                    }
                }

                //DEPARTAMENTO
                if(filtro.getDepartamento() != null && filtro.getDepartamento().getId() != null) 
                query.where(departamento.id.in(filtro.getDepartamento().getId()));

                //USUARIO
                if(filtro.getUsuario() != null && filtro.getUsuario().getId() != null) 
                query.where(usuario.id.in(filtro.getUsuario().getId()));

                //SERVIÇO
                if(filtro.getServico() != null && filtro.getServico().getId() != null) 
                query.where(servico.id.in(filtro.getServico().getId()));

                //EMPRESA
                if(filtro.getEmpresa() != null && filtro.getEmpresa().getId() != null) 
                query.where(empresa.id.in(filtro.getEmpresa().getId()));

            /*** FIM FILTRO SERVIÇOS PROGRAMADOS ***/

            return query.fetchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Long(-1);
        }
    }

    @Override
    public List<ServicoAgrupado> contadorServicoProgramadoGroupBy() {
        NumberPath<Long> aliasContagem = Expressions.numberPath(Long.class, "Contagem");
        JPAQuery query = new JPAQuery(em);
            query.select(Projections.constructor(ServicoAgrupado.class, servico.nome, servicoProgramado.count().as(aliasContagem)))
            .from(servicoProgramado)    
            .innerJoin(servicoProgramado.servico, servico)
            .groupBy(servico.id)
            .orderBy(aliasContagem.desc());
            
        return query.fetch();
    }
    
}
