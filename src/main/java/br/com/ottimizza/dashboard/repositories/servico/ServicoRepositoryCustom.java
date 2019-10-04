package br.com.ottimizza.dashboard.repositories.servico;

import br.com.ottimizza.dashboard.models.servicos.Servico;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;

public interface ServicoRepositoryCustom {
    
    Servico buscarServicoPorId(Long servicoId, Usuario usuario);
    
    Boolean verificarExistenciaServicoPorId(Long servicoId, Usuario usuario);
    
}
