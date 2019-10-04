package br.com.ottimizza.dashboard.repositories.graficoCaracteristica;

import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristicaID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraficoCaracteristicaRepository extends JpaRepository<GraficoCaracteristica, GraficoCaracteristicaID>, GraficoCaracteristicaRepositoryCustom{
    
}
