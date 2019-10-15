package br.com.ottimizza.dashboard.repositories.grafico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.math.BigInteger;
import java.util.List;

public interface GraficoRepositoryCustom {
    
    Grafico buscarGraficoPorId(BigInteger graficoId, Usuario usuario);
    
    Boolean verificarExistenciaGraficoPorId(BigInteger graficoId, Usuario usuario);
    
    boolean verificaExistenciaNomeDeGraficos(String nomeGrafico, BigInteger indicadorId, Usuario usuario) throws Exception;
    
    List<?> buscarListaDeGraficos(Usuario usuario);
    
    List<?> buscarListaDeGraficosPorIndicadorId(BigInteger indicadorId, Usuario usuario);
    
}
