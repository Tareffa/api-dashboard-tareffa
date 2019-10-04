package br.com.ottimizza.dashboard.repositories.graficoServico;

import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraficoServicoRepository extends JpaRepository<GraficoServico, GraficoServicoID>, GraficoServicoRepositoryCustom{
    
}
