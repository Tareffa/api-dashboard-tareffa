package br.com.ottimizza.dashboard.models.servicos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ServicoAgrupado {
    
    @Getter @Setter
    private String nomeServico;
    
    @Getter @Setter
    private Long servicosProgramadosContagem;
    
}
