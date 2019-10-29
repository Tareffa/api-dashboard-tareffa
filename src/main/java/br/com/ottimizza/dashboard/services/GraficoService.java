package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioDashboard;
import br.com.ottimizza.dashboard.repositories.caracteristica.CaracteristicaRepository;
import br.com.ottimizza.dashboard.repositories.grafico.GraficoRepository;
import br.com.ottimizza.dashboard.repositories.graficoCaracteristica.GraficoCaracteristicaRepository;
import br.com.ottimizza.dashboard.repositories.indicador.IndicadorRepository;
import java.math.BigInteger;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import br.com.ottimizza.dashboard.repositories.graficoServico.GraficoServicoRepository;
import br.com.ottimizza.dashboard.repositories.servico.ServicoRepository;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;
import java.util.Arrays;
import java.util.List;
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
    
    @Inject
    UsuarioRepository usuarioRepository;
    
    //*************************
    //*         CRUD          *
    //*************************
    
    //<editor-fold defaultstate="collapsed" desc="Get Grafico By Id">
    public JSONObject getGraficoById(BigInteger graficoId, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            message.put("status", "success");
            message.put("record", new JSONObject(graficoRepository.buscarGraficoPorId(graficoId, autenticado)));
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar o gráfico");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get List of Graficos">
    public JSONObject getListGraficos(Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            message.put("status", "success");
            message.put("records", new JSONArray(graficoRepository.buscarListaDeGraficos(autenticado)));
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar os gráficos");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Save">
    public JSONObject save(Grafico grafico, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            if(grafico.getIndicador().getId() == null) throw new Exception();
            
            if(!graficoRepository.verificaExistenciaNomeDeGraficos(grafico.getNomeGrafico(), grafico.getIndicador().getId(), autenticado)){
                Indicador indicador = indicadorRepository.buscarIndicadorPorId(grafico.getIndicador().getId(), autenticado);
                grafico.setIndicador(indicador);
                message.put("status", "success");
                message.put("record", new JSONObject(graficoRepository.save(grafico)));
            }else{
                message.put("status", "error");
                message.put("message", "Nome de gráfico já cadastrado!");
            }
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao salvar o gráfico");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete">
    public JSONObject delete(BigInteger graficoId, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            Grafico grafico = graficoRepository.buscarGraficoPorId(graficoId, autenticado);
            
            if(grafico != null){
                graficoCaracteristicaRepository.deleteGraficoCaracteristicaByGraficoId(graficoId);
                graficoServicoRepository.deleteGraficoServicoByGraficoId(graficoId);
                graficoRepository.deleteById(grafico.getId());
                message.put("status", "success");
                message.put("message", "Removido o gráfico com sucesso!");
            }else{
                message.put("status", "error");
                message.put("message", "Não é permitido excluir este gráfico!");
            }
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
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
                if(!graficoRepository.verificaExistenciaNomeDeGraficos(grafico.getNomeGrafico(), graficoReferencia.getIndicador().getId(), autenticado)){

                    graficoReferencia.setNomeGrafico(grafico.getNomeGrafico());
                    graficoRepository.save(graficoReferencia);
                    message.put("status", "success");
                    message.put("message", "Atualizado o gráfico com sucesso!");
                    return message;
                }else{
                    message.put("message", "Nome de gráfico já cadastrado!");
                }
            }else{
                message.put("message", "Não é permitido alterar este gráfico!");
            }
            message.put("status", "error");
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao atualizar o gráfico");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //*************************
    //*  GRAFICO - CONTAGENS  *
    //*************************
    
    //<editor-fold defaultstate="collapsed" desc="Count scheduled service">
    public JSONObject countServicoProgramado(BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado)throws Exception{
        JSONObject resultado = new JSONObject();
        JSONObject contagemServicoProgramado = new JSONObject();
        
        //ABERTO
        filtro.setSituacao(ServicoProgramadoSituacao.ABERTO);
        filtro.setPrazo(Arrays.asList(ServicoProgramadoPrazo.NO_PRAZO));
        contagemServicoProgramado.put("abertoNoPrazo", graficoRepository.contadorServicoProgramadoPorGraficoId(graficoId, filtro, autenticado));
        
        filtro.setPrazo(Arrays.asList(ServicoProgramadoPrazo.ATRASADO,ServicoProgramadoPrazo.VENCIDO));
        contagemServicoProgramado.put("abertoAtrasado", graficoRepository.contadorServicoProgramadoPorGraficoId(graficoId, filtro, autenticado));
        
        //ENCERRADO
        filtro.setSituacao(ServicoProgramadoSituacao.ENCERRADO);
        filtro.setPrazo(Arrays.asList(ServicoProgramadoPrazo.NO_PRAZO));
        contagemServicoProgramado.put("encerradoNoPrazo", graficoRepository.contadorServicoProgramadoPorGraficoId(graficoId, filtro, autenticado));
        filtro.setPrazo(Arrays.asList(ServicoProgramadoPrazo.ATRASADO,ServicoProgramadoPrazo.VENCIDO));
        contagemServicoProgramado.put("encerradoAtrasado", graficoRepository.contadorServicoProgramadoPorGraficoId(graficoId, filtro, autenticado));
        
        resultado.put("status", "success");
        resultado.put("records", contagemServicoProgramado);
        
        return resultado;
    }
    //</editor-fold>
    
    //*************************
    //*   GRAFICO - SERVICO   *
    //*************************
    
    //<editor-fold defaultstate="collapsed" desc="Buscar serviços relacionados ao gráfico Id">
    public JSONObject buscaServicosRelacionadosGraficoId(BigInteger graficoId, String descricao, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            
            //VALIDAÇÃO (GRÁFICO PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(graficoId, autenticado)){
                message.put("status", "success");
                message.put("records", new JSONArray(graficoServicoRepository.buscarServicosRelacionadosPorGraficoId(graficoId, descricao, autenticado)));
            }else{
                message.put("status", "error");
                message.put("message","Gráfico inválido!");
            }
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar os serviços relacionados");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Buscar serviços faltantes ao gráfico Id">
    public JSONObject buscaServicosFaltantesGraficoId(BigInteger graficoId, String descricao, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            
            //VALIDAÇÃO (GRÁFICO PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(graficoId, autenticado)){
                message.put("status", "success");
                message.put("records", new JSONArray(graficoServicoRepository.buscarServicosFaltantesPorGraficoId(graficoId, descricao, autenticado)));
            }else{
                message.put("status", "error");
                message.put("message","Gráfico inválido!");
            }
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar os serviços faltantes");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Insert grafico servico">
    public JSONObject saveGraficoServico(GraficoServico graficoServico, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            
            //VALIDAÇÃO (GRÁFICO E SERVIÇO PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(BigInteger.valueOf(graficoServico.getId().getGraficoId()), autenticado) && 
               servicoRepository.verificarExistenciaServicoPorId(graficoServico.getId().getServicoId(), autenticado)){
                //VERIFICA A EXISTÊNCIA DO GRÁFICO/SERVIÇO
                if(graficoServicoRepository.buscarGraficoServicoPorId(graficoServico.getId(), autenticado) == null){
                     graficoServicoRepository.save(graficoServico);
                    message.put("status", "success");
                    message.put("record", new JSONObject(graficoServicoRepository.buscarGraficoServicoPorId(graficoServico.getId(), autenticado)));
                    message.put("message","Inserido com sucesso");
                    return message;
                }else{
                    message.put("message","Gráfico/Serviço existente!");
                }
            }else{
                message.put("message","Gráfico ou Serviço Inválido!");
            }
            
            message.put("status", "error");
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao salvar o gráfico/serviço");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete grafico servico">
    public JSONObject deleteGraficoServico(GraficoServico graficoServico, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            
            if(graficoServicoRepository.buscarGraficoServicoPorId(graficoServico.getId(), autenticado) != null){    
                graficoServicoRepository.deleteById(graficoServico.getId());
                message.put("status", "success");
                message.put("message","Excluído com sucesso");
            }else{
                message.put("status", "error");
                message.put("message","Gráfico ou Serviço Inválido!");
            }
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao excluir o gráfico/serviço");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //********************************
    //*   GRAFICO - CARACTERISTICA   *
    //********************************
    
    //<editor-fold defaultstate="collapsed" desc="Buscar características relacionados ao gráfico Id">
    public JSONObject buscaCaracteristicasRelacionadosGraficoId(BigInteger graficoId, String descricao, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            
            //VALIDAÇÃO (GRÁFICO PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(graficoId, autenticado)){
                message.put("status", "success");
                message.put("records", new JSONArray(graficoCaracteristicaRepository.buscarCaracteristicasRelacionadosPorGraficoId(graficoId, descricao, autenticado)));
            }else{
                message.put("status", "error");
                message.put("message","Gráfico inválido!");
            }
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar as características relacionadas");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Buscar caracteristicas faltantes ao gráfico Id">
    public JSONObject buscaCaracteristicasFaltantesGraficoId(BigInteger graficoId, String descricao, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            
            //VALIDAÇÃO (GRÁFICO PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(graficoId, autenticado)){
                message.put("status", "success");
                message.put("records", new JSONArray(graficoCaracteristicaRepository.buscarCaracteristicasFaltantesPorGraficoId(graficoId, descricao, autenticado)));
            }else{
                message.put("status", "error");
                message.put("message","Gráfico inválido!");
            }
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao buscar as características faltantes");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Insert grafico característica">
    public JSONObject saveGraficoCaracteristica(GraficoCaracteristica graficoCaracteristica, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            //VALIDAÇÃO (GRÁFICO E CARACTERÍSTICA PERTENCE A CONTABILIDADE DO USUÁRIO LOGADO)
            if(graficoRepository.verificarExistenciaGraficoPorId(BigInteger.valueOf(graficoCaracteristica.getId().getGraficoId()), autenticado) && 
               caracteristicaRepository.verificarExistenciaCaracteristicaPorId(graficoCaracteristica.getId().getCaracteristicaId(), autenticado)){
                //VERIFICA A EXISTÊNCIA DO GRÁFICO/CARACTERÍSTICA
                if(graficoCaracteristicaRepository.buscarGraficoCaracteristicaPorId(graficoCaracteristica.getId(), autenticado) == null){
                    graficoCaracteristicaRepository.save(graficoCaracteristica);
                    message.put("status", "success");
                    message.put("record", new JSONObject(graficoCaracteristicaRepository.buscarGraficoCaracteristicaPorId(graficoCaracteristica.getId(), autenticado)));
                    message.put("message","Inserido com sucesso!");
                    return message;
                }else{
                    message.put("message","Gráfico da característica já cadastrado!");
                }
            }else{
                message.put("message","Gráfico ou característica inválido!");
            }
            message.put("status", "error");
            
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao salvar o gráfico/característica");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete gráfico característica">
    public JSONObject deleteGraficoCaracteristica(GraficoCaracteristica graficoCaracteristica, Usuario autenticado)throws Exception{
        JSONObject message = new JSONObject();
        try {
            
            if(graficoCaracteristicaRepository.buscarGraficoCaracteristicaPorId(graficoCaracteristica.getId(), autenticado) != null){
                graficoCaracteristicaRepository.deleteById(graficoCaracteristica.getId());
                message.put("status", "success");
                message.put("message","Excluído com sucesso!");
            }else{
                message.put("status", "error");
                message.put("message","Gráfico ou característica inválido!");
            }
            return message;
        } catch (Exception e) {
            message.put("status", "error");
            message.put("message", "Erro ao salvar o gráfico/característica");
            throw new Exception(message.toString());
        }
    }
    //</editor-fold>
    
    //*************************
    //*   GRAFICO - USUÁRIOS  *
    //*************************
    
    //<editor-fold defaultstate="collapsed" desc="Buscar usuários com total de serviços programados relacionados ao gráfico Id">
    public JSONObject buscaUsuariosComTotalServicosProgramadosPorGraficoId(BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado)throws Exception{
        JSONObject resultado = new JSONObject();
        List<UsuarioDashboard> usuarios = graficoRepository.buscarListaDeUsuariosPorGraficoId(graficoId, filtro, autenticado);
        resultado.put("status", "success");
        resultado.put("records", new JSONArray(usuarios));
        return resultado;
    }
    //</editor-fold>
}