package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.repositories.servicoProgramado.ServicoProgramadoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ServicoProgramadoService {

    @Inject
    private ServicoProgramadoRepository repository;

    //<editor-fold defaultstate="collapsed" desc="Save">
    public JSONObject count(ServicoProgramadoFiltroAvancado filtro)throws Exception{
        JSONObject resultado = new JSONObject();
        resultado.put("tamanho", repository.contadorServicoProgramado(filtro));
        return resultado;
    }
    //</editor-fold>
    
     //<editor-fold defaultstate="collapsed" desc="Save">
    public JSONObject countGroupBy()throws Exception{
        JSONObject resultado = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        resultado.put("resultado", new JSONArray(mapper.writeValueAsString(repository.contadorServicoProgramadoGroupBy())));
        return resultado;
    }
    //</editor-fold>

}
