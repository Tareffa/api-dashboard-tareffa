package br.com.ottimizza.dashboard.repositories.caracteristica;

import br.com.ottimizza.dashboard.models.QClassificacao;
import br.com.ottimizza.dashboard.models.caracteristica.Caracteristica;
import br.com.ottimizza.dashboard.models.caracteristica.QCaracteristica;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CaracteristicaRepositoryImpl implements CaracteristicaRepositoryCustom{
    
    @PersistenceContext
    EntityManager em;
    
    private QCaracteristica caracteristica = QCaracteristica.caracteristica;
    private QClassificacao classificacao = QClassificacao.classificacao;
    
    @Override
    public List<Caracteristica> buscaListaDeCaracteristicas(Usuario usuario, String descricao){
        
        try {
            JPAQuery<Caracteristica> query = new JPAQuery(em);
            query.from(caracteristica)
                .where(caracteristica.contabilidade.id.eq(usuario.getContabilidade().getId()))
                .where(Expressions.booleanTemplate(" {0} ILIKE {1} ", caracteristica.descricao, "%lucro%"));
                
            if(descricao != null){
                query.innerJoin(classificacao)
                    .on(caracteristica.classificacao.id.eq(classificacao.id))
                    .where(Expressions.booleanTemplate(" {0} ILIKE {1} ", classificacao.descricao, "%"+descricao+"%"));
            }
            
            return query.fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
