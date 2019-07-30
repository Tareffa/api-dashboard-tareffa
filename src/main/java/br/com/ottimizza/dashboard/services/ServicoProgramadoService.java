package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.repositories.servicoProgramado.ServicoProgramadoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.Tuple;
import java.util.List;

import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ServicoProgramadoService {
    
    private QServicoProgramado servicoProgramado = QServicoProgramado.servicoProgramado;
    private QServico servico = QServico.servico;

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
    public JSONObject countGroupBy(Short agrupamento)throws Exception{
        JSONObject resultado = new JSONObject();
        resultado.put("resultado", repository.contadorServicoProgramadoGroupBy(agrupamento));
        return resultado;
    }
    //</editor-fold>

}
