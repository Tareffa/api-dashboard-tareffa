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
 
	
	@Query(value = "SELECT new br.com.ottimizza.dashboard.models.indicadores.IndicadorTareffa(v.usuarioCliente , v.contador , v.empresa , v.servicoProgramado , v.contabilidade ,v.documento ) FROM IndicadorTareffa v")
	List<IndicadorTareffa> buscaIndicadoresTareffa();
}
