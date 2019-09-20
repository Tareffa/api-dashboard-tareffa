package br.com.ottimizza.dashboard.repositories.categoria;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.util.List;

public interface CategoriaRepositoryCustom {
    
    List<?> buscaListaDeCategorias(String descricao, Usuario usuario);
    
}
