package br.com.ottimizza.dashboard.repositories.graficoServico;

import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;

public interface GraficoServicoRepositoryCustom {
    
    GraficoServico buscarGraficoServicoPorId(GraficoServicoID graficoServicoId);
    
    Boolean verificarPermissaoGraficoServicoPorId(GraficoServicoID graficoServicoId, Usuario usuario);
    
}
