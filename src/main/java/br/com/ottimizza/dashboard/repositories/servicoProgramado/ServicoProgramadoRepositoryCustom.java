package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.util.List;

public interface ServicoProgramadoRepositoryCustom {
    
    Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro, Usuario autenticado);
    
    List<?> contadorServicoProgramadoGroupBy(Short tipoAgrupamento, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado);
    
}
