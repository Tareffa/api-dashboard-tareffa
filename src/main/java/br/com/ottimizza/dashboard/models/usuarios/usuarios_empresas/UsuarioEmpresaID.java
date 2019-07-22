package br.com.ottimizza.dashboard.models.usuarios.usuarios_empresas;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEmpresaID implements Serializable {

    @Getter
    @Setter
    @Column(name = "fk_usuarios_id")
    private BigInteger usuarioId;

    @Getter
    @Setter
    @Column(name = "fk_empresas_id")
    private BigInteger empresaId;
    
    //<editor-fold defaultstate="collapsed" desc="Equals & Hash Code">
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        UsuarioEmpresaID c = (UsuarioEmpresaID) obj;
        if (c == null) {
            return false;
        }
        if (c.usuarioId.equals(usuarioId) && c.empresaId.equals(empresaId)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return usuarioId.hashCode() + empresaId.hashCode();
    }

    //</editor-fold>
}
