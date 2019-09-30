package br.com.ottimizza.dashboard.repositories.Indicador;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.indicadores.QIndicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
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
    
}
