package br.com.ottimizza.dashboard.repositories.Indicador;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.math.BigInteger;
import java.util.List;

public interface IndicadorRepositoryCustom {
    
    Indicador buscarIndicadorPorId(BigInteger indicadorId, Usuario usuario);
    
    List<?> buscarListaDeIndicadores(Usuario usuario);
    
}
