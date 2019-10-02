package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.Grafico.GraficoRepository;
import br.com.ottimizza.dashboard.repositories.Indicador.IndicadorRepository;
import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GraficoService {
    
    @Inject
    GraficoRepository graficoRepository;
    
    @Inject
    IndicadorRepository indicadorRepository;
    
    //<editor-fold defaultstate="collapsed" desc="Get Grafico By Id">
    public Grafico getGraficoById(BigInteger graficoId, Usuario autenticado)throws Exception{
        try {
            return graficoRepository.buscarGraficoPorId(graficoId, autenticado);
        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", "Erro ao buscar o gráfico");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get List of Graficos">
    public List getListGraficos(Usuario autenticado)throws Exception{
        try {
            return indicadorRepository.buscarListaDeIndicadores(autenticado);
        } catch (Exception e) {
            JSONObject message = new JSONObject();
            message.put("message", "Erro ao buscar os gráficos");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save">
    public Grafico save(Grafico grafico, Usuario autenticado)throws Exception{
        try {
            if(grafico.getIndicador().getId() == null) throw new Exception();
            Indicador indicador = indicadorRepository.buscarIndicadorPorId(grafico.getIndicador().getId(), autenticado);
            grafico.setIndicador(indicador);
            return graficoRepository.save(grafico);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao salvar o gráfico");
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
                message.put("message", "Removido o gráfico com sucesso!");
                return message;
            }
            message.put("message", "Não é permitido excluir este gráfico!");
            return message;
        } catch (Exception e) {
            message.put("message", "Erro ao excluir o gráfico");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Update">
    public JSONObject update(BigInteger id, Grafico grafico,Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            Grafico graficoReferencia = graficoRepository.buscarGraficoPorId(id, autenticado);
            if(graficoReferencia != null){
                graficoReferencia.setNomeGrafico(grafico.getNomeGrafico());
                graficoRepository.save(graficoReferencia);
                message.put("message", "Atualizado o gráfico com sucesso!");
                return message;
            }
            message.put("message", "Não é permitido alterar este gráfico!");
            return message;
        } catch (Exception e) {
            message.put("message", "Erro ao atualizar o gráfico");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
}
