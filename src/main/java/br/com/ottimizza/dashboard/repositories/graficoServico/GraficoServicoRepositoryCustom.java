package br.com.ottimizza.dashboard.repositories.graficoServico;

import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;

public interface GraficoServicoRepositoryCustom {
    
    GraficoServico buscarGraficoServicoPorId(GraficoServicoID graficoServicoId);
    
}
