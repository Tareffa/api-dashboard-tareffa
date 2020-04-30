package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.grafico.GraficoRepository;
import br.com.ottimizza.dashboard.repositories.graficoServico.GraficoServicoRepository;
import br.com.ottimizza.dashboard.repositories.graficoCaracteristica.GraficoCaracteristicaRepository;
import br.com.ottimizza.dashboard.repositories.indicador.IndicadorRepository;
import java.math.BigInteger;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class IndicadorService {
    
    @Inject
    IndicadorRepository indicadorRepository;
    
    @Inject
    GraficoRepository graficoRepository;
    
    @Inject
    GraficoCaracteristicaRepository graficoCaracteristicaRepository;
    
    @Inject
    GraficoServicoRepository graficoServicoRepository;
    
    @Inject
    GraficoService graficoService;
    
    /************
    *   CRUD    *
    *************/
    
    //<editor-fold defaultstate="collapsed" desc="Get Indicador By Id">
    public JSONObject getIndicadorById(BigInteger indicadorId, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            message.put("status", "success");
            message.put("record", new JSONObject(indicadorRepository.buscarIndicadorPorId(indicadorId, autenticado)));
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
            message.put("status", "success");
            message.put("records", new JSONArray(indicadorRepository.buscarListaDeIndicadores(autenticado)));
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
            if(!indicadorRepository.verificaExistenciaDescricaoDeIndicadores(indicador.getDescricao(), autenticado)){
                message.put("status", "success");
                message.put("record", new JSONObject(indicadorRepository.save(indicador)));
                message.put("message", "Indicador criado com sucesso!");
            }else{
                message.put("status", "error");
                message.put("message", "Descrição de indicador já cadastrado!");
            }
                
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
            message.put("status", "success");
            if(indicador != null){
                graficoCaracteristicaRepository.deleteGraficoCaracteristicaByIndicadorId(id, autenticado.getContabilidade().getId());
                graficoServicoRepository.deleteGraficoServicoByIndicadorId(id, autenticado.getContabilidade().getId());
                graficoRepository.deleteGraficoByIndicadorId(id, autenticado.getContabilidade().getId());
                indicadorRepository.deleteById(indicador.getId());
                
                message.put("message", "Removido o indicador com sucesso!");
                return message;
            }
            
            message.put("message", "Não é permitido excluir este indicador!");
            return message;
        } catch (Exception e) {
            e.printStackTrace();
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
            if(!indicadorRepository.verificaExistenciaDescricaoDeIndicadores(indicador.getDescricao(), autenticado)){
                if(indicadorReferencia != null){
                    indicadorReferencia.setDescricao(indicador.getDescricao());
                    indicadorRepository.save(indicadorReferencia);
                    message.put("message", "Atualizado o indicador com sucesso!");
                    message.put("status", "success");
                    return message;
                }
                message.put("message", "Não é permitido alterar este indicador!");
            }else{
                message.put("message", "Descrição de indicador já cadastrado!");
            }
            message.put("status", "error");
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao atualizar o indicador");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>

    //*************************
    //*  GRAFICO - CONTAGENS  *
    //*************************
    
    //<editor-fold defaultstate="collapsed" desc="Count scheduled service">
    public JSONObject countServicoProgramado(BigInteger indicadorId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado)throws Exception{
        JSONObject resultado = new JSONObject();
        
        resultado.put("status", "success");
        resultado.put("records", new JSONArray(graficoRepository.buscarListaDeGraficosDashboardPorIndicadorId(indicadorId, filtro, autenticado)));
        
        return resultado;
    }
    //</editor-fold>
    
    /*GRÁFICO*/

    //<editor-fold defaultstate="collapsed" desc="Get List of Graphic - Indicadores">
    public JSONObject getListGraphicFromIndicadores(BigInteger indicadorId, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            message.put("status", "success");
            message.put("records", new JSONArray(graficoRepository.buscarListaDeGraficosPorIndicadorId(indicadorId, autenticado)));
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar os gráficos dos indicadores");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
}
