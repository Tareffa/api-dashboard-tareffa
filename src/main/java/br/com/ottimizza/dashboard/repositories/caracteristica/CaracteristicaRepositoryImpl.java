package br.com.ottimizza.dashboard.repositories.caracteristica;

import br.com.ottimizza.dashboard.models.QClassificacao;
import br.com.ottimizza.dashboard.models.caracteristica.CaracteristicaShort;
import br.com.ottimizza.dashboard.models.caracteristica.QCaracteristica;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.types.Projections;
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
    public List<CaracteristicaShort> buscaListaDeCaracteristicas(Usuario usuario, String descricao){
        
        try {
            JPAQuery<CaracteristicaShort> query = new JPAQuery(em);
            query.from(caracteristica)
                .where(caracteristica.contabilidade.id.eq(usuario.getContabilidade().getId()));
                
            if(descricao != null){
                query.innerJoin(classificacao)
                    .on(caracteristica.classificacao.id.eq(classificacao.id))
                    .where(classificacao.descricao.like(descricao+"%"));
            }
            
            //SELECT
            query.select(Projections.constructor(CaracteristicaShort.class,caracteristica.id,caracteristica.descricao));
            return query.fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean verificarExistenciaCaracteristicaPorId(Long caracteristicaId, Usuario usuario) {
        try {
            JPAQuery<CaracteristicaShort> query = new JPAQuery(em);
            query.from(caracteristica)
                .where(caracteristica.contabilidade.id.eq(usuario.getContabilidade().getId()))
                .where(caracteristica.id.eq(caracteristicaId));

            return query.fetchCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
