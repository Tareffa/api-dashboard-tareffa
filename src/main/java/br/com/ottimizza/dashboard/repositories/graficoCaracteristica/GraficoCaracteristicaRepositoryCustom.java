package br.com.ottimizza.dashboard.repositories.graficoCaracteristica;

import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristicaID;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.math.BigInteger;
import java.util.List;

public interface GraficoCaracteristicaRepositoryCustom {

    GraficoCaracteristica buscarGraficoCaracteristicaPorId(GraficoCaracteristicaID graficoCaracteristicaId, Usuario usuario);

    List<?> buscarCaracteristicasRelacionadosPorGraficoId(BigInteger graficoId, Usuario usuario);
    
    List<?> buscarCaracteristicasFaltantesPorGraficoId(BigInteger graficoId, Usuario usuario);
    
}