package br.com.ottimizza.dashboard.repositories.grafico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.GraficoDashboard;
import br.com.ottimizza.dashboard.models.graficos.GraficoShort;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioDashboard;
import java.math.BigInteger;
import java.util.List;

public interface GraficoRepositoryCustom {
    
    Grafico buscarGraficoPorId(BigInteger graficoId, Usuario usuario);
    
    Boolean verificarExistenciaGraficoPorId(BigInteger graficoId, Usuario usuario);
    
    boolean verificaExistenciaNomeDeGraficos(String nomeGrafico, BigInteger indicadorId, Usuario usuario) throws Exception;
    
    List<?> buscarListaDeGraficos(Usuario usuario);
    
    List<GraficoShort> buscarListaDeGraficosPorIndicadorId(BigInteger indicadorId, Usuario usuario);
    
    List<GraficoDashboard> buscarListaDeGraficosDashboardPorIndicadorId(BigInteger indicadorId, ServicoProgramadoFiltroAvancado filtro, Usuario usuario);
    
    Long contadorServicoProgramadoPorGraficoId(BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado);
    
    List<UsuarioDashboard> buscarListaDeUsuariosPorGraficoId(BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario usuario);   
}