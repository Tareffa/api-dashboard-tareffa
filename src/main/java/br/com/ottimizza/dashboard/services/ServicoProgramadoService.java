package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancadoDataProgramado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.servicoProgramado.ServicoProgramadoRepository;
import java.util.Calendar;
import java.util.Date;

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
        resultado.put("tamanho", repository.contadorServicoProgramado(filtro, autenticado));
        return resultado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Count Scheduled Service Group By">
    public JSONObject countGroupBy(Short agrupamento, Long limit, String beforeServicoDataEntrega, String beforeServicoNome, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado)throws Exception{
        JSONObject resultado = new JSONObject();
        
        System.out.println("___CORTANDO A DATA___");
        System.out.println("ANO:" + beforeServicoDataEntrega.substring(beforeServicoDataEntrega.indexOf("-")));
        System.out.println("MES:" + beforeServicoDataEntrega.substring(beforeServicoDataEntrega.indexOf("-"),beforeServicoDataEntrega.lastIndexOf("-")));
        System.out.println("DIA:" + beforeServicoDataEntrega.substring(beforeServicoDataEntrega.lastIndexOf("-")));
        //Calendar calendario = new Calendar();
        
        Date data = new Date(beforeServicoDataEntrega);
        System.out.println("DATE UTIL: " + data);
        resultado.put("resultado", repository.contadorServicoProgramadoGroupBy(agrupamento, limit, data, beforeServicoNome, filtro, autenticado));
        return resultado;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Lista de Responsável Empresa Data Término">
    public JSONObject listaEmpresaResponsavelDataTermino(Long idServico, Long limit, Long beforeServicoProgramaId, String beforeCodigoErp, ServicoProgramadoFiltroAvancadoDataProgramado filtro, Usuario autenticado)throws Exception{
        JSONObject resultado = new JSONObject();
        resultado.put("resultado", repository.listaEmpresaResponsavelDataTermino(idServico, limit, beforeServicoProgramaId, beforeCodigoErp, filtro, autenticado));
        return resultado;
    }
    //</editor-fold>

}
