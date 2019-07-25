package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.repositories.servicoProgramado.ServicoProgramadoRepository;

import javax.inject.Inject;
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
        System.out.println("Gerando Mensagem");
        return resultado;
    }
    //</editor-fold>

}
