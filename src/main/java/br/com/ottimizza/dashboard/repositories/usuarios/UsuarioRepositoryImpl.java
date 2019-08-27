package br.com.ottimizza.dashboard.repositories.usuarios;

import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import br.com.ottimizza.dashboard.models.contabilidade.QContabilidadeShort;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    private QUsuario usuario = QUsuario.usuario;
    private QContabilidadeShort contabilidade = QContabilidadeShort.contabilidadeShort;

    @Override
    public Usuario findByEmail(String email) {
        JPAQuery<Usuario> query = new JPAQuery<Usuario>(em).from(usuario).where(usuario.email.eq(email));
        return query.fetchFirst();
    }

    @Override
    public String findLogoAccountingFromUser(Long contabilidadeId) {
        try {
            JPAQuery<ContabilidadeShort> query = new JPAQuery<ContabilidadeShort>(em).from(contabilidade).where(contabilidade.id.eq(contabilidadeId));
            ContabilidadeShort resultado = query.fetchFirst();
            return resultado.getUrlLogotipo();
        } catch (Exception e) {
            return "Error";
        }
    }

}
