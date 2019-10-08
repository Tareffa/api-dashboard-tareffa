package br.com.ottimizza.dashboard.repositories.graficoServico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.QGrafico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.QGraficoServico;
import br.com.ottimizza.dashboard.models.indicadores.QIndicador;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoShort;
import br.com.ottimizza.dashboard.models.servicos.Servico;
import br.com.ottimizza.dashboard.models.servicos.ServicoShort;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GraficoServicoRepositoryImpl implements GraficoServicoRepositoryCustom{

    @PersistenceContext
    EntityManager em;
    
    private QServico servico = QServico.servico;
    private QServicoShort servicoShort = QServicoShort.servicoShort;
    private QGrafico grafico = QGrafico.grafico;
    private QIndicador indicador = QIndicador.indicador;
    private QGraficoServico graficoServico = QGraficoServico.graficoServico;
    
    //<editor-fold defaultstate="collapsed" desc="Busca de gráfico serviço específico por Id">
    @Override
    public GraficoServico buscarGraficoServicoPorId(GraficoServicoID graficoServicoId, Usuario usuario) {
        try {
            JPAQuery<GraficoServico> query = new JPAQuery(em);
            query.from(graficoServico)
                .where(graficoServico.grafico.id.eq(BigInteger.valueOf(graficoServicoId.getGraficoId())))
                .where(graficoServico.servico.id.eq(graficoServicoId.getServicoId()))
                .where(graficoServico.servico.contabilidade.id.eq(usuario.getContabilidade().getId()));

            query.select(Projections.constructor(GraficoServico.class, graficoServico.id, graficoServico.grafico, graficoServico.servico));
            
            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Verifica permição ao uso do gráfico serviço">
    @Override
    public Boolean verificarPermissaoGraficoServicoPorId(GraficoServicoID graficoServicoId, Usuario usuario) {
        try {
            JPAQuery<Grafico> queryGrafico = new JPAQuery(em);
            queryGrafico.from(grafico)
                .innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())) //CONTABILIDADE
                .where(grafico.id.eq(BigInteger.valueOf(graficoServicoId.getGraficoId()))); //INDICADOR ID

            JPAQuery<Servico> queryServico = new JPAQuery(em);
            queryServico.from(servico)
                .where(servico.contabilidade.id.eq(usuario.getContabilidade().getId()))
                .where(servico.id.eq(graficoServicoId.getServicoId()));

            if(queryGrafico.fetchCount() > 0 && queryServico.fetchCount() > 0){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return null;
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Busca de serviços relacionados por gráfico Id">
    @Override
    public List<?> buscarServicosRelacionadosPorGraficoId(BigInteger graficoId, Usuario usuario) {
        try {
            JPAQuery<ServicoShort> query = new JPAQuery(em);
            query.from(servico)
                .innerJoin(graficoServico).on(graficoServico.servico.id.eq(servico.id))
                .where(graficoServico.grafico.id.eq(graficoId));

            query.select(Projections.constructor(ServicoShort.class, servico.id, servico.nome, servico.contabilidade, servico.permiteBaixaManual));

            return query.fetch();
        } catch (Exception e) {
            return null;
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Busca de serviços faltantes por gráfico Id">
    @Override
    public List<?> buscarServicosFaltantesPorGraficoId(BigInteger graficoId, Usuario usuario) {
        try {
            
            JPAQuery<ServicoShort> query = new JPAQuery(em);
            query.from(servico)
                .leftJoin(graficoServico).on(
                    graficoServico.servico.id.eq(servico.id)
                    .and(graficoServico.grafico.id.eq(graficoId)))
                .where(servico.contabilidade.id.eq(usuario.getContabilidade().getId()))
                .where(graficoServico.grafico.id.isNull());
            
            query.select(Projections.constructor(ServicoShort.class, servico.id, servico.nome, servico.contabilidade, servico.permiteBaixaManual));

            return query.distinct().fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
}
