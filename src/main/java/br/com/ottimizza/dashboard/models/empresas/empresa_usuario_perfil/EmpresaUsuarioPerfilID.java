package br.com.ottimizza.dashboard.models.empresas.empresa_usuario_perfil;

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
public class EmpresaUsuarioPerfilID implements Serializable{
    
    @Getter
    @Setter
    @Column(name = "fk_empresas_id")
    private Long empresaId;

    @Getter
    @Setter
    @Column(name = "fk_usuarios_id")
    private Long usuarioId;

    @Getter
    @Setter
    @Column(name = "fk_perfis_id")
    private Long perfilId;    

    //<editor-fold defaultstate="collapsed" desc="HashCode & Equals">
    @Override
    public int hashCode() {
        return empresaId.intValue() + usuarioId.intValue() + perfilId.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        EmpresaUsuarioPerfilID c = (EmpresaUsuarioPerfilID) obj;
        if (c == null) {
            return false;
        }
        if (c.empresaId.equals(empresaId) && c.usuarioId.equals(usuarioId) && c.perfilId.equals(perfilId) ) {
            return true;
        }
        return false;
    }
    //</editor-fold>
        
}
