package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;

import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public String findLogoAccountingFromUser(Long contabilidadeId) throws Exception {
        return usuarioRepository.findLogoAccountingFromUser(contabilidadeId);
    }

}
