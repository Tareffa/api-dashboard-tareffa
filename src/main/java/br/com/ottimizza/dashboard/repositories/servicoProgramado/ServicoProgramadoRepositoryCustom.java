package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;

public interface ServicoProgramadoRepositoryCustom {
    
    Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro);
    
}
