package br.com.ottimizza.dashboard.repositories.Grafico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.math.BigInteger;

public interface GraficoRepositoryCustom {
    
    Grafico buscarGraficoPorId(BigInteger indicadorId, Usuario usuario);
    
}
