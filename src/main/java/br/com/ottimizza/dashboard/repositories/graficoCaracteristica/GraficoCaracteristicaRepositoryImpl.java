package br.com.ottimizza.dashboard.repositories.graficoCaracteristica;

import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristicaID;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.QGraficoCaracteristica;
import br.com.ottimizza.dashboard.models.caracteristica.CaracteristicaShort;
import br.com.ottimizza.dashboard.models.caracteristica.QCaracteristica;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GraficoCaracteristicaRepositoryImpl implements GraficoCaracteristicaRepositoryCustom{
    
    @PersistenceContext
    EntityManager em;
    
    private QGraficoCaracteristica graficoCaracteristica = QGraficoCaracteristica.graficoCaracteristica;
    private QCaracteristica caracteristica = QCaracteristica.caracteristica;

    @Override
    public GraficoCaracteristica buscarGraficoCaracteristicaPorId(GraficoCaracteristicaID graficoCaracteristicaId, Usuario usuario) {
        try {
            JPAQuery<GraficoCaracteristica> query = new JPAQuery(em);
            query.from(graficoCaracteristica)
                .where(graficoCaracteristica.grafico.id.eq(BigInteger.valueOf(graficoCaracteristicaId.getGraficoId())))
                .where(graficoCaracteristica.caracteristica.id.eq(graficoCaracteristicaId.getCaracteristicaId()))
                .where(graficoCaracteristica.caracteristica.contabilidade.id.eq(usuario.getContabilidade().getId()));

            query.select(Projections.constructor(GraficoCaracteristica.class, graficoCaracteristica.id, graficoCaracteristica.grafico, graficoCaracteristica.caracteristica));
            
            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<?> buscarCaracteristicasRelacionadosPorGraficoId(BigInteger graficoId, Usuario usuario) {
        try {
            JPAQuery<CaracteristicaShort> query = new JPAQuery(em);
            query.from(caracteristica)
                .innerJoin(graficoCaracteristica).on(graficoCaracteristica.caracteristica.id.eq(caracteristica.id))
                .where(graficoCaracteristica.grafico.id.eq(graficoId));

            query.select(Projections.constructor(CaracteristicaShort.class, caracteristica.id, caracteristica.descricao));

            return query.fetch();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<?> buscarCaracteristicasFaltantesPorGraficoId(BigInteger graficoId, Usuario usuario) {
        try {
            JPAQuery<CaracteristicaShort> query = new JPAQuery(em);
            query.from(caracteristica)
                .leftJoin(graficoCaracteristica).on(
                    graficoCaracteristica.caracteristica.id.eq(caracteristica.id)
                    .and(graficoCaracteristica.grafico.id.eq(graficoId)))
                .where(caracteristica.contabilidade.id.eq(usuario.getContabilidade().getId()))
                .where(graficoCaracteristica.grafico.id.isNull());
            
            query.select(Projections.constructor(CaracteristicaShort.class, caracteristica.id, caracteristica.descricao));

            return query.distinct().fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
