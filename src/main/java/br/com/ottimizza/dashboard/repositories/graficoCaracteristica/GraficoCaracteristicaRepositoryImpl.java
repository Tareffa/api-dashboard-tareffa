package br.com.ottimizza.dashboard.repositories.graficoCaracteristica;

import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristicaID;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.QGraficoCaracteristica;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GraficoCaracteristicaRepositoryImpl implements GraficoCaracteristicaRepositoryCustom{
    
    @PersistenceContext
    EntityManager em;
    
    private QGraficoCaracteristica graficoCaracteristica = QGraficoCaracteristica.graficoCaracteristica;

    @Override
    public GraficoCaracteristica buscarGraficoCaracteristicaPorId(GraficoCaracteristicaID graficoCaracteristicaId) {
        try {
            JPAQuery<GraficoCaracteristica> query = new JPAQuery(em);
            query.from(graficoCaracteristica)
                .where(graficoCaracteristica.grafico.id.eq(BigInteger.valueOf(graficoCaracteristicaId.getGraficoId())))
                .where(graficoCaracteristica.caracteristica.id.eq(graficoCaracteristicaId.getCaracteristicaId()));

            query.select(Projections.constructor(GraficoCaracteristica.class, graficoCaracteristica.id, graficoCaracteristica.grafico, graficoCaracteristica.caracteristica));
            
            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }
    
}
