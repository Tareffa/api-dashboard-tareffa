package br.com.ottimizza.dashboard.repositories.indicador;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.indicadores.IndicadorShort;
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
public class IndicadorRepositoryImpl implements IndicadorRepositoryCustom{

    @PersistenceContext
    EntityManager em;
    
    private QIndicador indicador = QIndicador.indicador;
    
    @Override
    public Indicador buscarIndicadorPorId(BigInteger indicadorId, Usuario usuario) {
        try {
            JPAQuery<Indicador> query = new JPAQuery(em);
            query.from(indicador)
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())) //CONTABILIDADE
                .where(indicador.id.eq(indicadorId)); //INDICADOR ID

            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<?> buscarListaDeIndicadores(Usuario usuario) {
        try {
            JPAQuery<Indicador> query = new JPAQuery(em);
            query.from(indicador).where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())); //CONTABILIDADE
            query.select(Projections.constructor(IndicadorShort.class, indicador.id, indicador.descricao));
            
            return query.fetch();
        } catch (Exception e) {
            return null;
        }
    }
    
}
