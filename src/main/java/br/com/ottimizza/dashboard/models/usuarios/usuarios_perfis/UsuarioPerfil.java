package br.com.ottimizza.dashboard.models.usuarios.usuarios_perfis;

import br.com.ottimizza.dashboard.models.usuarios.UsuarioShort;
import br.com.ottimizza.dashboard.models.perfis.Perfil;
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
@Table(name = "ot_usuarios_perfis")
public class UsuarioPerfil implements Serializable {

    @Getter
    @Setter
    @EmbeddedId
    private UsuarioPerfilID id;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_usuarios_id")
    @JoinColumn(name = "fk_usuarios_id") //, insertable = false, updatable = false
    private UsuarioShort usuario;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_perfis_id")
    @JoinColumn(name = "fk_perfis_id") // , insertable = false, updatable = false
    private Perfil perfil;
}