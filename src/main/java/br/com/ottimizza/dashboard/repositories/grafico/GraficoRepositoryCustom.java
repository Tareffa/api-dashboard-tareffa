package br.com.ottimizza.dashboard.repositories.grafico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.math.BigInteger;
import java.util.List;

public interface GraficoRepositoryCustom {
    
    Grafico buscarGraficoPorId(BigInteger indicadorId, Usuario usuario);
    
    List<?> buscarListaDeGraficos(Usuario usuario);
    
}
