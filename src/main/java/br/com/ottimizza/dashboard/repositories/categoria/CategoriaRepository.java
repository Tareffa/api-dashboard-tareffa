package br.com.ottimizza.dashboard.repositories.categoria;

import br.com.ottimizza.dashboard.models.categoria.CategoriaShort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaShort,Long>, CategoriaRepositoryCustom{
    
}
