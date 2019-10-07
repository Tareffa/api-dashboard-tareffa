package br.com.ottimizza.dashboard.models.servicos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ServicoAgrupado {
    
    private Long id;
    
    private String nomeServico;
    
    private Long servicosProgramadosContagem;
    
}
