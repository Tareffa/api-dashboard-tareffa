package br.com.ottimizza.dashboard.repositories.grafico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.GraficoShort;
import br.com.ottimizza.dashboard.models.graficos.QGrafico;
import br.com.ottimizza.dashboard.models.indicadores.QIndicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
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
            
            return query.fetch();
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

}
