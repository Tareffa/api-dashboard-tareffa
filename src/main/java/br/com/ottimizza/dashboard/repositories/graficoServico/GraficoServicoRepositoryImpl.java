package br.com.ottimizza.dashboard.repositories.graficoServico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.QGrafico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.QGraficoServico;
import br.com.ottimizza.dashboard.models.indicadores.QIndicador;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.Servico;
import br.com.ottimizza.dashboard.models.servicos.ServicoShort;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.types.Projections;
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
    private QGrafico grafico = QGrafico.grafico;
    private QIndicador indicador = QIndicador.indicador;
    private QGraficoServico graficoServico = QGraficoServico.graficoServico;
    
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
 
    //<editor-fold defaultstate="collapsed" desc="Busca de serviços relacionados por gráfico Id">
        @Override
        public List<?> buscarServicosRelacionadorPorGraficoId(BigInteger graficoId, Usuario usuario) {
            try {
            JPAQuery<ServicoShort> query = new JPAQuery(em);
            query.from(servico)
                .innerJoin(graficoServico).on(graficoServico.servico.id.eq(servico.id))
                .where(graficoServico.grafico.id.eq(graficoId));
            
            return query.fetch();
        } catch (Exception e) {
            return null;
        }
        }
    //</editor-fold>

}
