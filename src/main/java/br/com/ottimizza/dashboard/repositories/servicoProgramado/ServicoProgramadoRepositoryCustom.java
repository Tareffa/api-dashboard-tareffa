package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import com.querydsl.core.Tuple;
import java.util.List;

public interface ServicoProgramadoRepositoryCustom {
    
    Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro);
    
    List<Tuple> contadorServicoProgramadoGroupBy();
    
}
