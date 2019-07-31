package br.com.ottimizza.dashboard.repositories.usuarios;

import br.com.ottimizza.dashboard.models.users.QUser;
import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    private QUsuario usuario = QUsuario.usuario;

    @Override
    public Usuario findByEmail(String email) {
        JPAQuery<Usuario> query = new JPAQuery<Usuario>(em).from(usuario).where(usuario.email.eq(email));
        return query.fetchFirst();
    }

}
