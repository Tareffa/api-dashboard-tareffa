package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import com.querydsl.core.Tuple;
import java.util.List;
import java.util.Map;

public interface ServicoProgramadoRepositoryCustom {
    
    Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro);
    
    Map<String, Long> contadorServicoProgramadoGroupBy();
    
}
