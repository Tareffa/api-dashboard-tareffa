package br.com.ottimizza.dashboard.repositories.indicador;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;

public interface IndicadorRepository extends JpaRepository<Indicador, BigInteger>, IndicadorRepositoryCustom{
    
}
