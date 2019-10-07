package br.com.ottimizza.dashboard.repositories.graficoServico;

import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.math.BigInteger;
import java.util.List;

public interface GraficoServicoRepositoryCustom {
    
    GraficoServico buscarGraficoServicoPorId(GraficoServicoID graficoServicoId, Usuario usuario);
    
    Boolean verificarPermissaoGraficoServicoPorId(GraficoServicoID graficoServicoId, Usuario usuario);
    
    List<?> buscarServicosRelacionadorPorGraficoId(BigInteger graficoId, Usuario usuario);
    
}
