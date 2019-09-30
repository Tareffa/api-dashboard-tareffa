package br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica;

import br.com.ottimizza.dashboard.models.caracteristica.Caracteristica;
import br.com.ottimizza.dashboard.models.graficos.Grafico;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "ot_grafico_caracteristica")
@NoArgsConstructor @AllArgsConstructor
public class GraficoCaracteristica implements Serializable{
    
    @EmbeddedId
    private GraficoCaracteristicaID id;
    
    @ManyToOne
    @MapsId("fk_grafico_id")
    @JoinColumn(name = "fk_grafico_id", insertable = false, updatable = false)
    private Grafico grafico;

    @ManyToOne
    @MapsId("fk_caracteristica_id")
    @JoinColumn(name = "fk_caracteristica_id", insertable = false, updatable = false)
    private Caracteristica caracteristica;
    
}
