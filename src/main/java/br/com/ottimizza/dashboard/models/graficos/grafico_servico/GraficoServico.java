package br.com.ottimizza.dashboard.models.graficos.grafico_servico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
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
@Getter @Setter
@Table(name = "ot_grafico_servico")
@NoArgsConstructor @AllArgsConstructor
public class GraficoServico implements Serializable{
    
    @EmbeddedId
    private GraficoServicoID id;
    
    @ManyToOne
    @MapsId("fk_grafico_id")
    @JoinColumn(name = "fk_grafico_id", insertable = false, updatable = false)
    private Grafico grafico;

    @ManyToOne
    @MapsId("fk_servico_id")
    @JoinColumn(name = "fk_servico_id", insertable = false, updatable = false)
    private Servico servico;
    
}
