package br.com.ottimizza.dashboard.repositories.caracteristica;

import br.com.ottimizza.dashboard.models.categoria.CategoriaShort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaracteristicaRepository extends JpaRepository<CategoriaShort,Long>, CaracteristicaRepositoryCustom{
    
}
