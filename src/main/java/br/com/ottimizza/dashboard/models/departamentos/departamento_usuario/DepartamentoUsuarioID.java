/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ottimizza.dashboard.models.departamentos.departamento_usuario;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author User
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoUsuarioID implements Serializable{
    
    @Getter
    @Setter
    @Column(name = "fk_departamentos_id")
    private Long departamentoId;

    @Getter
    @Setter
    @Column(name = "fk_usuarios_id")
    private Long usuarioId;
    
    //<editor-fold defaultstate="collapsed" desc="HashCode & Equals">
    @Override
    public int hashCode() {
        return departamentoId.intValue() + usuarioId.intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        DepartamentoUsuarioID c = (DepartamentoUsuarioID) obj;
        if (c == null) {
            return false;
        }
        if (c.departamentoId.equals(departamentoId) && c.usuarioId.equals(usuarioId)) {
            return true;
        }
        return false;
    }
    //</editor-fold>
}
