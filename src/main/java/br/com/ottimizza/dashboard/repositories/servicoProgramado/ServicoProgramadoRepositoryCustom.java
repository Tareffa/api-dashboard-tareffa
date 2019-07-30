package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import java.util.List;

public interface ServicoProgramadoRepositoryCustom {
    
    Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro);
    
    List<?> contadorServicoProgramadoGroupBy(Short tipoAgrupamento, ServicoProgramadoFiltroAvancado filtro);
    
}
