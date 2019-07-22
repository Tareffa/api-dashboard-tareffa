package br.com.ottimizza.dashboard.models.usuarios.usuarios_perfis;

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
public class UsuarioPerfilID implements Serializable {

    @Getter
    @Setter
    @Column(name = "fk_usuarios_id")
    private BigInteger usuarioId;

    @Getter
    @Setter
    @Column(name = "fk_perfis_id")
    private BigInteger perfilId;
    
    //<editor-fold defaultstate="collapsed" desc="Equals & Hash Code">
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        UsuarioPerfilID c = (UsuarioPerfilID) obj;
        if (c == null) {
            return false;
        }
        if (c.usuarioId.equals(usuarioId) && c.perfilId.equals(perfilId)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return usuarioId.hashCode() + perfilId.hashCode();
    }

    //</editor-fold>
}