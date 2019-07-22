package br.com.ottimizza.dashboard.models.caracteristica.caracteristica_empresas;

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
public class CaracteristicaEmpresaID implements Serializable{
    
    @Getter
    @Setter
    @Column(name = "fk_caracteristicas_id")
    private Long caracteristicaId;
    
    @Getter
    @Setter
    @Column(name = "fk_empresas_id")
    private Long empresaId;

    //<editor-fold defaultstate="collapsed" desc="HashCode & Equals">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.caracteristicaId);
        hash = 71 * hash + Objects.hashCode(this.empresaId);
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
        final CaracteristicaEmpresaID other = (CaracteristicaEmpresaID) obj;
        if (!Objects.equals(this.caracteristicaId, other.caracteristicaId)) {
            return false;
        }
        if (!Objects.equals(this.empresaId, other.empresaId)) {
            return false;
        }
        return true;
    }
    //</editor-fold>
    
}
