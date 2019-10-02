package br.com.ottimizza.dashboard.repositories.grafico.GraficoServico;

import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraficoServicoRepository extends JpaRepository<GraficoServico, BigInteger>, GraficoServicoRepositoryCustom{
    
}
