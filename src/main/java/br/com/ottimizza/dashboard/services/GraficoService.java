package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.Grafico.GraficoRepository;
import java.math.BigInteger;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GraficoService {
    
    @Inject
    GraficoRepository graficoRepository;
    
    //<editor-fold defaultstate="collapsed" desc="Save">
    public Grafico save(Grafico grafico, Usuario autenticado)throws Exception{
        try {
            return graficoRepository.save(grafico);
        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", "Erro ao salvar o gráfico");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete">
    public JSONObject delete(BigInteger id, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            Grafico grafico = graficoRepository.buscarGraficoPorId(id, autenticado);
            if(grafico != null){
                graficoRepository.deleteById(grafico.getId());
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
