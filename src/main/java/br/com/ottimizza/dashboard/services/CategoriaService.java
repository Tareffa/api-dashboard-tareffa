package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.categoria.CategoriaRepository;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    
    @Inject
    private CategoriaRepository categoriaRepository;
    
    //<editor-fold defaultstate="collapsed" desc="Busca lista de categorias">
    public JSONObject getCategorias(String descricao, Usuario autenticado) throws Exception {
        JSONObject response = new JSONObject();
        try {
            response.put("status", "success");
            response.put("records", categoriaRepository.buscaListaDeCategorias(descricao, autenticado));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    //</editor-fold>
    
}
