package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;

import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    public JSONObject findImagesFromUser(Long usuarioId) throws Exception {
        JSONObject resultado = new JSONObject(usuarioRepository.findImagesFromUser(usuarioId));
        return resultado;
    }
    
    public JSONObject findLogoAccountingFromUser(Long contabilidadeId) throws Exception {
        JSONObject resultado = new JSONObject();
        resultado.put("logoUrl", usuarioRepository.findLogoAccountingFromUser(contabilidadeId));
        return resultado;
    }
}
