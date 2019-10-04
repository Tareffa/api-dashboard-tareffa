package br.com.ottimizza.dashboard.repositories.servico;

import br.com.ottimizza.dashboard.models.servicos.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long>, ServicoRepositoryCustom{
    
}
