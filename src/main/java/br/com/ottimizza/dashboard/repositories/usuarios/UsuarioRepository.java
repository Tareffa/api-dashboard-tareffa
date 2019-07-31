package br.com.ottimizza.dashboard.repositories.usuarios;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, BigInteger>, UsuarioRepositoryCustom {

}
