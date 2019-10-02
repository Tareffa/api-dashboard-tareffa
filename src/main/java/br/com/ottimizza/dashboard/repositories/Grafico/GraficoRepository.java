package br.com.ottimizza.dashboard.repositories.Grafico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GraficoRepository extends JpaRepository<Grafico, BigInteger>, GraficoRepositoryCustom{
    
}
