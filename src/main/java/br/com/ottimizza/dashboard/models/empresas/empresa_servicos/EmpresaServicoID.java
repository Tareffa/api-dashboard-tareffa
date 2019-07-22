package br.com.ottimizza.dashboard.models.empresas.empresa_servicos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaServicoID implements Serializable {

    @Getter
    @Setter
    @Column(name = "fk_empresas_id")
    private Long empresaId;

    @Getter
    @Setter
    @Column(name = "fk_servicos_id")
    private Long servicoId;
    
    //<editor-fold defaultstate="collapsed" desc="HashCode & Equals">
    @Override
    public int hashCode() {
        return empresaId.intValue() + servicoId.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        EmpresaServicoID c = (EmpresaServicoID) obj;
        if (c == null) {
            return false;
        }
        if (c.empresaId.equals(empresaId) && c.servicoId.equals(servicoId)) {
            return true;
        }
        return false;
    }
    //</editor-fold>

}
