package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramado;
import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoProgramadoRepository extends JpaRepository<ServicoProgramado, BigInteger>, ServicoProgramadoRepositoryCustom {

//	Optional<Company> findById(BigInteger idCompany);
    
}
