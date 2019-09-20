package br.com.ottimizza.dashboard.repositories.categoria;

import br.com.ottimizza.dashboard.models.categoria.CategoriaDescricaoShort;
import br.com.ottimizza.dashboard.models.categoria.QCategoriaShort;
import br.com.ottimizza.dashboard.models.contabilidade.QContabilidadeID;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CategoriaRepositoryImpl implements CategoriaRepositoryCustom{
    
    @PersistenceContext
    EntityManager em;
    
    private QCategoriaShort categoria = QCategoriaShort.categoriaShort;
    private QContabilidadeID contabilidadeID = QContabilidadeID.contabilidadeID;
    
    @Override
    public List buscaListaDeCategorias(String descricao, Usuario usuario){
        
        try {
            JPAQuery query = new JPAQuery(em);
            query.from(categoria).where(categoria.contabilidade.id.eq(usuario.getContabilidade().getId()));

            if(descricao != null){
                query.where(categoria.descricao.like(descricao+"%"));
            }
            
            //SELECT
            query.select(Projections.constructor(CategoriaDescricaoShort.class,categoria.id,categoria.descricao));
            return query.fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
