package br.com.ottimizza.dashboard.models.caracteristica.caracteristica_servicos;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CaracteristicaServicoID implements Serializable {
    
    @Getter
    @Setter
    @Column(name = "fk_caracteristicas_id")
    private Long caracteristicaId;

    @Getter
    @Setter
    @Column(name = "fk_servicos_id")
    private Long servicoId;
    
    @Getter
    @Setter
    @Column(name = "fk_regra_caracteristica_servico_id")
    private Long regraCaracteristicaServicoId;
    
    //<editor-fold defaultstate="collapsed" desc="HashCode & Equals">
    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CaracteristicaServicoID other = (CaracteristicaServicoID) obj;
        if (!Objects.equals(this.caracteristicaId, other.caracteristicaId)) {
            return false;
        }
        if (!Objects.equals(this.servicoId, other.servicoId)) {
            return false;
        }
        return true;
    }
    //</editor-fold>
    
}
