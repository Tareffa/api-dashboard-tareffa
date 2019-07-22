package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import java.util.List;

public interface ServicoProgramadoRepositoryCustom {
    
    Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro);
    
//    List<ServicoProgramado> findCompaniesByCNPJ(List<String> cnpj);
//    List<Company> findCompanyByName(String name);
//
//    List<Company> findCompanyByName(String name, Integer pageSize, Integer pageIndex);
//    
}
