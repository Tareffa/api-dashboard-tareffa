package br.com.ottimizza.dashboard.repositories.usuarios;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.math.BigInteger;

public interface UsuarioRepositoryCustom {

    Usuario findByEmail(String email);
    
    String findLogoAccountingFromUser(Long contabilidadeId);

    Long contadorServicoProgramadoPorUsuarioGraficoId(Long usuarioId, BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado);
    
}
