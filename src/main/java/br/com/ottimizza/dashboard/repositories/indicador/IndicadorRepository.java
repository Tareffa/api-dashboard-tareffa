package br.com.ottimizza.dashboard.repositories.indicador;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.indicadores.IndicadorTareffa;

@Repository
public interface IndicadorRepository extends JpaRepository<Indicador, BigInteger>, IndicadorRepositoryCustom{
 
	
	@Query(value = "SELECT new br.com.ottimizza.dashboard.models.indicadores.IndicadorTareffa(v.usuarios_clientes , v.contadores , v.empresas , v.servicos_programados , v.contabilidades ,v.documentos ) FROM view_tareffa_indicadores_site v", nativeQuery = true)
	List<IndicadorTareffa> buscaIndicadoresTareffa();
}
