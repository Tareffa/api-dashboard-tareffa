package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.caracteristica.CaracteristicaRepository;
import br.com.ottimizza.dashboard.repositories.grafico.GraficoRepository;
import br.com.ottimizza.dashboard.repositories.graficoCaracteristica.GraficoCaracteristicaRepository;
import br.com.ottimizza.dashboard.repositories.indicador.IndicadorRepository;
import java.math.BigInteger;
import java.util.List;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import br.com.ottimizza.dashboard.repositories.graficoServico.GraficoServicoRepository;
import br.com.ottimizza.dashboard.repositories.servico.ServicoRepository;
import org.json.JSONArray;

@Service
public class GraficoService {
    
    @Inject
    GraficoRepository graficoRepository;
    
    @Inject
    GraficoServicoRepository graficoServicoRepository;
    
    @Inject
    GraficoCaracteristicaRepository graficoCaracteristicaRepository;
    
    @Inject
    ServicoRepository servicoRepository;
    
    @Inject
    CaracteristicaRepository caracteristicaRepository;
    
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
            return graficoRepository.buscarListaDeGraficos(autenticado);
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
            }else{
                message.put("message", "Não é permitido excluir este gráfico!");
            }
            
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
            }else{
                message.put("message", "Não é permitido alterar este gráfico!");
            }
            
            return message;
            
        } catch (Exception e) {
            message.put("message", "Erro ao atualizar o gráfico");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //*************************
    //*   GRAFICO - SERVICO   *
    //*************************
    
    //<editor-fold defaultstate="collapsed" desc="Insert grafico servico">
    public JSONObject saveGraficoServico(GraficoServico graficoServico, Usuario autenticado)throws Exception{
        JSONObject resposta = new JSONObject();
        try {
            
            //VALIDAÇÃO (GRÁFICO E SERVIÇO PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(BigInteger.valueOf(graficoServico.getId().getGraficoId()), autenticado) && 
               servicoRepository.verificarExistenciaServicoPorId(graficoServico.getId().getServicoId(), autenticado)){
                //VERIFICA A EXISTÊNCIA DO GRÁFICO/SERVIÇO
                if(graficoServicoRepository.buscarGraficoServicoPorId(graficoServico.getId(), autenticado) == null){
                     graficoServicoRepository.save(graficoServico);
                     resposta = new JSONObject(graficoServicoRepository.buscarGraficoServicoPorId(graficoServico.getId(), autenticado));
                }else{

                }
            }else{
                resposta.put("message","Gráfico ou Serviço Inválido!");
            }
            
            return resposta;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao salvar o gráfico/serviço");
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete grafico servico">
    public JSONObject deleteGraficoServico(GraficoServico graficoServico, Usuario autenticado)throws Exception{
        JSONObject resposta = new JSONObject();
        try {
            
            if(graficoServicoRepository.buscarGraficoServicoPorId(graficoServico.getId(), autenticado) != null){    
                graficoServicoRepository.deleteById(graficoServico.getId());
                resposta.put("message","Excluído com sucesso");
            }else{
                resposta.put("message","Gráfico ou Serviço Inválido!");
            }
            
            return resposta;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao excluir o gráfico/serviço");
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Buscar serviços relacionados ao gráfico Id">
    public JSONObject buscaServicosRelacionadosGraficoId(BigInteger graficoId, Usuario autenticado)throws Exception{
        JSONObject resposta = new JSONObject();
        try {
            
            //VALIDAÇÃO (GRÁFICO PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(graficoId, autenticado)){
               resposta.put("records", new JSONArray(graficoServicoRepository.buscarServicosRelacionadorPorGraficoId(graficoId, autenticado)));
            }else{
                resposta.put("message","Gráfico inválido!");
            }
            
            return resposta;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao buscar os serviços relacionados");
        }
    }
    //</editor-fold>
    
    //********************************
    //*   GRAFICO - CARACTERISTICA   *
    //********************************
    
    //<editor-fold defaultstate="collapsed" desc="Insert grafico característica">
    public JSONObject saveGraficoCaracteristica(GraficoCaracteristica graficoCaracteristica, Usuario autenticado)throws Exception{
        JSONObject resposta = new JSONObject();
        try {
            //VALIDAÇÃO (GRÁFICO E CARACTERÍSTICA PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(BigInteger.valueOf(graficoCaracteristica.getId().getGraficoId()), autenticado) && 
               caracteristicaRepository.verificarExistenciaCaracteristicaPorId(graficoCaracteristica.getId().getCaracteristicaId(), autenticado)){
                //VERIFICA A EXISTÊNCIA DO GRÁFICO/CARACTERÍSTICA
                if(graficoCaracteristicaRepository.buscarGraficoCaracteristicaPorId(graficoCaracteristica.getId(), autenticado) == null){
                    graficoCaracteristicaRepository.save(graficoCaracteristica);
                    resposta = new JSONObject(graficoCaracteristicaRepository.buscarGraficoCaracteristicaPorId(graficoCaracteristica.getId(), autenticado));
                }else{
                    resposta.put("message","Gráfico da característica já cadastrado!");
                }
            }else{
                resposta.put("message","Gráfico ou característica inválido!");
            }
            
            return resposta;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao salvar o gráfico/característica");
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete gráfico característica">
    public JSONObject deleteGraficoCaracteristica(GraficoCaracteristica graficoCaracteristica, Usuario autenticado)throws Exception{
        JSONObject resposta = new JSONObject();
        try {
            
            if(graficoCaracteristicaRepository.buscarGraficoCaracteristicaPorId(graficoCaracteristica.getId(), autenticado) != null){
                graficoCaracteristicaRepository.deleteById(graficoCaracteristica.getId());
                resposta.put("message","Excluído com sucesso!");
            }else{
                resposta.put("message","Gráfico ou característica inválido!");
            }
            
            return resposta;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erro ao excluir o gráfico/característica");
        }
    }
    //</editor-fold>
}
