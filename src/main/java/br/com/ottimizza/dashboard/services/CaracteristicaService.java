package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class CaracteristicaService {
    
    @Inject
    //private CategoriaRepository categoriaRepository;
    
    //<editor-fold defaultstate="collapsed" desc="Busca lista de categorias">
    public JSONObject getCategorias(String descricao, Usuario autenticado) throws Exception {
        JSONObject response = new JSONObject();
        try {
            response.put("status", "sucesso");
            response.put("resultado", "");//categoriaRepository.buscaListaDeCategorias(autenticado)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
    //</editor-fold>
    
}
