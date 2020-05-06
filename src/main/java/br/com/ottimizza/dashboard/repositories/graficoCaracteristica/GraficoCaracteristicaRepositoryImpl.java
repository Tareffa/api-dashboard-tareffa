package br.com.ottimizza.dashboard.repositories.graficoCaracteristica;

import br.com.ottimizza.dashboard.models.QClassificacao;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristicaID;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.QGraficoCaracteristica;
import br.com.ottimizza.dashboard.models.caracteristica.CaracteristicaShort;
import br.com.ottimizza.dashboard.models.caracteristica.QCaracteristica;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.models.usuarios.usuarios_unidade_negocio.QUsuarioUnidadeNegocio;
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
    private QClassificacao classificacao = QClassificacao.classificacao;
    private QUsuarioUnidadeNegocio usuarioUnidadeNegocio = QUsuarioUnidadeNegocio.usuarioUnidadeNegocio;

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
    public List<?> buscarCaracteristicasRelacionadosPorGraficoId(BigInteger graficoId, String descricao, Usuario autenticado) {
        try {
            JPAQuery<CaracteristicaShort> query = new JPAQuery(em);
            query.from(caracteristica)
                .innerJoin(graficoCaracteristica).on(graficoCaracteristica.caracteristica.id.eq(caracteristica.id))
                .where(graficoCaracteristica.grafico.id.eq(graficoId));

            boolean restringirUnidadeNegocio = (autenticado.getRestringirUnidadeNegocio() != null && autenticado.getRestringirUnidadeNegocio());
            if(restringirUnidadeNegocio){
                query.innerJoin(usuarioUnidadeNegocio)
                    .on(graficoCaracteristica.caracteristica.id.eq(usuarioUnidadeNegocio.id.unidadeNegocioId)
                        .and(usuarioUnidadeNegocio.id.usuarioId.eq(autenticado.getId())));
            }

            if(descricao != null){
                query.innerJoin(classificacao)
                    .on(caracteristica.classificacao.id.eq(classificacao.id))
                    .where(classificacao.descricao.like(descricao+"%"));
            }

            query.select(Projections.constructor(CaracteristicaShort.class, caracteristica.id, caracteristica.descricao));

            return query.orderBy(caracteristica.descricao.asc()).fetch();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<?> buscarCaracteristicasFaltantesPorGraficoId(BigInteger graficoId, String descricao, Usuario autenticado) {
        try {
            JPAQuery<CaracteristicaShort> query = new JPAQuery(em);
            query.from(caracteristica)
                .leftJoin(graficoCaracteristica).on(
                    graficoCaracteristica.caracteristica.id.eq(caracteristica.id)
                    .and(graficoCaracteristica.grafico.id.eq(graficoId)))
                .where(caracteristica.contabilidade.id.eq(autenticado.getContabilidade().getId()))
                .where(graficoCaracteristica.grafico.id.isNull());

            boolean restringirUnidadeNegocio = (autenticado.getRestringirUnidadeNegocio() != null && autenticado.getRestringirUnidadeNegocio());
            if(restringirUnidadeNegocio){
                query.innerJoin(usuarioUnidadeNegocio)
                    .on(graficoCaracteristica.caracteristica.id.eq(usuarioUnidadeNegocio.id.unidadeNegocioId)
                        .and(usuarioUnidadeNegocio.id.usuarioId.eq(autenticado.getId())));
            }

            if(descricao != null){
                query.innerJoin(classificacao)
                    .on(caracteristica.classificacao.id.eq(classificacao.id))
                    .where(classificacao.descricao.like(descricao+"%"));
            }
            
            query.select(Projections.constructor(CaracteristicaShort.class, caracteristica.id, caracteristica.descricao));

            return query.orderBy(caracteristica.descricao.asc()).distinct().fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
