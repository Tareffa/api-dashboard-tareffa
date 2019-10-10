package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.indicador.IndicadorRepository;
import java.math.BigInteger;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class IndicadorService {
    
    @Inject
    IndicadorRepository indicadorRepository;
    
    //<editor-fold defaultstate="collapsed" desc="Get Indicador By Id">
    public JSONObject getIndicadorById(BigInteger indicadorId, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            message.put("status", "sucesso");
            message.put("record", indicadorRepository.buscarIndicadorPorId(indicadorId, autenticado));
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar o indicador");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get List of Indicadores">
    public JSONObject getListIndicadores(Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            message.put("status", "sucesso");
            message.put("record", indicadorRepository.buscarListaDeIndicadores(autenticado));
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar os indicadores");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save">
    public JSONObject save(Indicador indicador, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            indicador.setContabilidade(autenticado.getContabilidade());
            message.put("status", "sucesso");
            message.put("record",indicadorRepository.save(indicador));
            return message;
        } catch (Exception e) {
            message.put("status", "error");
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
            message.put("status", "sucesso");
            if(indicador != null){
                indicadorRepository.deleteById(indicador.getId());
                message.put("message", "Removido o indicador com sucesso!");
                return message;
            }
            
            message.put("message", "Não é permitido excluir este indicador!");
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao excluir o indicador");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Update">
    public JSONObject update(BigInteger id, Indicador indicador,Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            Indicador indicadorReferencia = indicadorRepository.buscarIndicadorPorId(id, autenticado);
            message.put("status", "sucesso");
            if(indicadorReferencia != null){
                indicadorReferencia.setDescricao(indicador.getDescricao());
                indicadorRepository.save(indicadorReferencia);
                message.put("message", "Atualizado o indicador com sucesso!");
                return message;
            }
            message.put("message", "Não é permitido alterar este indicador!");
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao atualizar o indicador");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
}
