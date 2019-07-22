package br.com.ottimizza.dashboard.models.empresas;

import br.com.ottimizza.dashboard.models.caracteristica.Caracteristica;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class EmpresaCaracteristiscasWrapper {

    @Getter
    @Setter
    private Empresa empresa;
    
    @Getter
    @Setter
    private List<Caracteristica> caracteristicas;
    
}
