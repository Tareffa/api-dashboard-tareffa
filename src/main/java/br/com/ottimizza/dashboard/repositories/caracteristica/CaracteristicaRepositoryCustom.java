package br.com.ottimizza.dashboard.repositories.caracteristica;

import br.com.ottimizza.dashboard.models.caracteristica.CaracteristicaShort;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.util.List;

public interface CaracteristicaRepositoryCustom {
    
    List<CaracteristicaShort> buscaListaDeCaracteristicas(Usuario usuario, String Descricao);
    
}
