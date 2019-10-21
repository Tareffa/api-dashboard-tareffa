package br.com.ottimizza.dashboard.repositories.grafico;

import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoStatus;
import br.com.ottimizza.dashboard.models.caracteristica.caracteristica_empresas.QCaracteristicaEmpresa;
import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.GraficoShort;
import br.com.ottimizza.dashboard.models.graficos.QGrafico;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.QGraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.QGraficoServico;
import br.com.ottimizza.dashboard.models.indicadores.QIndicador;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GraficoRepositoryImpl implements GraficoRepositoryCustom{
    
    @PersistenceContext
    EntityManager em;
    
    private QGrafico grafico = QGrafico.grafico;
    private QIndicador indicador = QIndicador.indicador;
    
    private QServicoProgramado servicoProgramado = QServicoProgramado.servicoProgramado;
    private QGraficoServico graficoServico = QGraficoServico.graficoServico;
    
    private QGraficoCaracteristica graficoCaracteristica = QGraficoCaracteristica.graficoCaracteristica;
    private QCaracteristicaEmpresa caracteristicaEmpresa = QCaracteristicaEmpresa.caracteristicaEmpresa;

    @Override
    public Grafico buscarGraficoPorId(BigInteger graficoId, Usuario usuario) {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico)
                .innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())) //CONTABILIDADE
                .where(grafico.id.eq(graficoId)); //INDICADOR ID

            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<?> buscarListaDeGraficos(Usuario usuario) {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico).innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())); //CONTABILIDADE
            query.select(Projections.constructor(GraficoShort.class, grafico.id, grafico.nomeGrafico));
            
            return query.orderBy(grafico.nomeGrafico.asc()).fetch();
        } catch (Exception e) {
            return null;
        }
    }    

    @Override
    public Boolean verificarExistenciaGraficoPorId(BigInteger graficoId, Usuario usuario) {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico)
                .innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())) //CONTABILIDADE
                .where(grafico.id.eq(graficoId)); //INDICADOR ID

            return query.fetchCount() > 0;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GraficoShort> buscarListaDeGraficosPorIndicadorId(BigInteger indicadorId, Usuario usuario) {
        try {
            JPAQuery<GraficoShort> query = new JPAQuery(em);
            query.from(grafico).innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())) //CONTABILIDADE
                .where(indicador.id.eq(indicadorId));
            query.select(Projections.constructor(GraficoShort.class, grafico.id, grafico.nomeGrafico));
            
            return query.orderBy(grafico.nomeGrafico.asc()).fetch();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean verificaExistenciaNomeDeGraficos(String nomeGrafico, BigInteger indicadorId, Usuario usuario) throws Exception {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico).innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())) //CONTABILIDADE
                .where(indicador.id.eq(indicadorId)) //CONTABILIDADE
                .where(grafico.nomeGrafico.eq(nomeGrafico));
            
            return query.fetchCount() > 0;
        } catch (Exception e) {
            throw new Exception("Error");
        }
    }

    @Override
    public Long contadorServicoProgramadoPorGraficoId(BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado) {
        try {
        if(autenticado == null) return 0L;
        
        JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(graficoServico).on(
                    servicoProgramado.servico.id.eq(graficoServico.servico.id)
                    .and(graficoServico.grafico.id.eq(graficoId))
                    ) //JOIN GRAFICO/SERVICO
                .innerJoin(caracteristicaEmpresa).on(servicoProgramado.cliente.id.eq(caracteristicaEmpresa.empresa.id)) //JOIN CARACTERISTICA/EMPRESA
                .innerJoin(graficoCaracteristica).on(
                    caracteristicaEmpresa.caracteristica.id.eq(graficoCaracteristica.caracteristica.id)
                    .and(graficoCaracteristica.grafico.id.eq(graficoId))
                ); //JOIN GRÁFICO/CARACTERÍSTICA
            
            /*** FILTRO SERVIÇOS PROGRAMADOS ***/
                //CONTABILIDADE
                //query.where(servico.contabilidade.id.eq(autenticado.getContabilidade().getId()));

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
}