package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancadoDataProgramado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.servicoProgramado.ServicoProgramadoRepository;
import java.util.Arrays;

import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ServicoProgramadoService {
    
    private QServicoProgramado servicoProgramado = QServicoProgramado.servicoProgramado;
    private QServico servico = QServico.servico;

    @Inject
    private ServicoProgramadoRepository repository;

    //<editor-fold defaultstate="collapsed" desc="Count scheduled service">
    public JSONObject count(ServicoProgramadoFiltroAvancado filtro, Usuario autenticado)throws Exception{
        JSONObject resultado = new JSONObject();
        JSONObject contagemServicoProgramado = new JSONObject();
        
        //ABERTO
        filtro.setSituacao(ServicoProgramadoSituacao.ABERTO);
        filtro.setPrazo(Arrays.asList(ServicoProgramadoPrazo.NO_PRAZO));
        contagemServicoProgramado.put("abertoNoPrazo", repository.contadorServicoProgramado(filtro, autenticado));
        
        filtro.setPrazo(Arrays.asList(ServicoProgramadoPrazo.ATRASADO,ServicoProgramadoPrazo.VENCIDO));
        contagemServicoProgramado.put("abertoAtrasado", repository.contadorServicoProgramado(filtro, autenticado));
        
        //ENCERRADO
        filtro.setSituacao(ServicoProgramadoSituacao.ENCERRADO);
        filtro.setPrazo(Arrays.asList(ServicoProgramadoPrazo.NO_PRAZO));
        contagemServicoProgramado.put("encerradoNoPrazo", repository.contadorServicoProgramado(filtro, autenticado));
        filtro.setPrazo(Arrays.asList(ServicoProgramadoPrazo.ATRASADO,ServicoProgramadoPrazo.VENCIDO));
        contagemServicoProgramado.put("encerradoAtrasado", repository.contadorServicoProgramado(filtro, autenticado));
        
        resultado.put("status", "success");
        resultado.put("records", contagemServicoProgramado);
        
        return resultado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Count Scheduled Service Group By">
    public JSONObject countGroupBy(Short agrupamento, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado)throws Exception{
        JSONObject resultado = new JSONObject();
        
        resultado.put("status", "success");
        resultado.put("records", repository.contadorServicoProgramadoGroupBy(agrupamento, filtro, autenticado));
        return resultado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Lista de Responsável Empresa Data Término">
    public JSONObject listaEmpresaResponsavelDataTermino(Long idServico, Long limit, Long beforeServicoProgramaId, String beforeCodigoErp, ServicoProgramadoFiltroAvancadoDataProgramado filtro, Usuario autenticado)throws Exception{
        JSONObject resultado = new JSONObject();
        
        resultado.put("status", "success");
        resultado.put("records", repository.listaEmpresaResponsavelDataTermino(idServico, limit, beforeServicoProgramaId, beforeCodigoErp, filtro, autenticado));
        return resultado;
    }
    //</editor-fold>

}
