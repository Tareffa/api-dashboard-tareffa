package br.com.ottimizza.dashboard.repositories.Grafico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.QGrafico;
import br.com.ottimizza.dashboard.models.indicadores.QIndicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
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
    public Grafico buscarGraficoPorId(BigInteger indicadorId, Usuario usuario) {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico)
                .innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())) //CONTABILIDADE
                .where(grafico.id.eq(indicadorId)); //INDICADOR ID

            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }
    
}
