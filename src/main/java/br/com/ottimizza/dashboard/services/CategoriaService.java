package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.categoria.CategoriaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;

public class CategoriaService {
    
    @Inject
    private CategoriaRepository categoriaRepository;
    
    //<editor-fold defaultstate="collapsed" desc="Busca lista de categorias">
    public JSONObject getCategorias(String descricao, Usuario autenticado) throws Exception {
        JSONObject response = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.put("status", "success");
            response.put("records", new JSONArray(mapper.writeValueAsString(categoriaRepository.buscaListaDeCategorias(autenticado))));
        } catch (Exception e) {
            throw e;
        }
        return response;
    }
    //</editor-fold>
    
}
