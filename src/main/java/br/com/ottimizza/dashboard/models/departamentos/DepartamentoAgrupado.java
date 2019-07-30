package br.com.ottimizza.dashboard.models.departamentos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoAgrupado {
    
    @Getter @Setter
    private String nomeDepartamento;
    
    @Getter @Setter
    private Long servicosProgramadosContagem; 
    
}
