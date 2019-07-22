package br.com.ottimizza.dashboard.models.caracteristica.caracteristica_servicos;

import br.com.ottimizza.dashboard.models.caracteristica.Caracteristica;
import br.com.ottimizza.dashboard.models.regras.RegraCaracteristicaServico;
import br.com.ottimizza.dashboard.models.servicos.Servico;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_caracteristicas_servicos")
public class CaracteristicaServico implements Serializable {
    
    @Getter
    @Setter
    @EmbeddedId
    private CaracteristicaServicoID id;
    
    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_caracteristicas_id")
    @JoinColumn(name = "fk_caracteristicas_id", insertable = false, updatable = false)
    private Caracteristica caracteristica;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_servicos_id")
    @JoinColumn(name = "fk_servicos_id", insertable = false, updatable = false)
    private Servico servico;
    
    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_regra_caracteristica_servico_id")
    @JoinColumn(name = "fk_regra_caracteristica_servico_id", insertable = false, updatable = false)
    private RegraCaracteristicaServico regraCaracteristicaServico;
    
}
