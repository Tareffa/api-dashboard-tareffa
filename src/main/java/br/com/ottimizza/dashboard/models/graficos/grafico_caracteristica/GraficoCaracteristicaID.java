package br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class GraficoCaracteristicaID implements Serializable{
    
    @Column(name = "fk_grafico_id")
    private Long graficoId;
    
    @Column(name = "fk_caracteristica_id")
    private Long caracteristicaId;
    
}
