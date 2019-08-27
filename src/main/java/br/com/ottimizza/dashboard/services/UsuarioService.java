package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;

import java.util.List;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public JSONObject findLogoAccountingFromUser(Long contabilidadeId) throws Exception {
        JSONObject resultado = new JSONObject();
        resultado.put("logoUrl", usuarioRepository.findLogoAccountingFromUser(contabilidadeId));
        return resultado;
    }

}
