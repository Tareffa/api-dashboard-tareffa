package br.com.ottimizza.dashboard.models.graficos.grafico_servico;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class GraficoServicoID implements Serializable{
    
    @Column(name = "fk_grafico_id")
    private Long graficoId;
    
    @Column(name = "fk_servico_id")
    private Long servicoId;
    
}
