package br.com.ottimizza.dashboard.repositories.usuarios;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;

public interface UsuarioRepositoryCustom {

    Usuario findByEmail(String email);

}
