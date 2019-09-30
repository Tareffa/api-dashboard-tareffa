package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.Indicador.IndicadorRepository;
import java.math.BigInteger;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class IndicadorService {
    
    @Inject
    IndicadorRepository indicadorRepository;
    
    //<editor-fold defaultstate="collapsed" desc="Save">
    public Indicador save(Indicador indicador, Usuario autenticado)throws Exception{
        try {
            indicador.setContabilidade(autenticado.getContabilidade());
            return indicadorRepository.save(indicador);
        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", "Erro ao salvar o indicador");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete">
    public JSONObject delete(BigInteger id, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            Indicador indicador = indicadorRepository.buscarIndicadorPorId(id, autenticado);
            if(indicador != null){
                indicadorRepository.deleteById(indicador.getId());
                message.put("message", "Removido o indicador com sucesso!");
                return message;
            }
            
            message.put("message", "Não é permitido excluir este indicador!");
            return message;
        } catch (Exception e) {
            message.put("message", "Erro ao excluir o indicador");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
}
