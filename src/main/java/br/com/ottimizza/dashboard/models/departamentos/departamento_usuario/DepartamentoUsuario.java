/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ottimizza.dashboard.models.departamentos.departamento_usuario;

import br.com.ottimizza.dashboard.models.departamentos.Departamento;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
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

/**
 *
 * @author User
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_departamentos_usuarios")
public class DepartamentoUsuario implements Serializable{
    
    @Getter
    @Setter
    @EmbeddedId
    private DepartamentoUsuarioID id;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_departamentos_id")
    @JoinColumn(name = "fk_departamentos_id", insertable = false, updatable = false)
    private Departamento departamento;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_usuarios_id")
    @JoinColumn(name = "fk_usuarios_id", insertable = false, updatable = false)
    private Usuario usuario;
    
}
