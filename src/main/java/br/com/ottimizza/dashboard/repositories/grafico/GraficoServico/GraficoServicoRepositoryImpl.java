package br.com.ottimizza.dashboard.repositories.grafico.GraficoServico;

import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.QGraficoServico;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GraficoServicoRepositoryImpl implements GraficoServicoRepositoryCustom{

    @PersistenceContext
    EntityManager em;
    
    private QGraficoServico graficoServico = QGraficoServico.graficoServico;
    
    @Override
    public GraficoServico buscarGraficoServicoPorId(GraficoServicoID graficoServicoId) {
        try {
            JPAQuery<GraficoServico> query = new JPAQuery(em);
            query.from(graficoServico)
                .where(graficoServico.grafico.id.eq(BigInteger.valueOf(graficoServicoId.getGraficoId())))
                .where(graficoServico.servico.id.eq(graficoServicoId.getServicoId()));

            query.select(Projections.constructor(GraficoServico.class, graficoServico.id, graficoServico.grafico, graficoServico.servico));
            
            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }
    
}
