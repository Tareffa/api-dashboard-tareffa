package br.com.ottimizza.dashboard.repositories.graficoCaracteristica;

import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristicaID;

public interface GraficoCaracteristicaRepositoryCustom {

    GraficoCaracteristica buscarGraficoCaracteristicaPorId(GraficoCaracteristicaID graficoCaracteristicaId);

}