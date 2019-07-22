package br.com.ottimizza.dashboard.models.empresas.empresa_usuario_perfil;

import br.com.ottimizza.dashboard.models.empresas.Empresa;
import br.com.ottimizza.dashboard.models.perfis.Perfil;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioShort;
import br.com.ottimizza.dashboard.models.empresas.empresa_usuario_perfil.EmpresaUsuarioPerfilID;
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
@Table(name = "ot_usuarios_empresas_perfis")
public class EmpresaUsuarioPerfil implements Serializable{
    
    @Getter
    @Setter
    @EmbeddedId
    private EmpresaUsuarioPerfilID id;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_empresas_id")
    @JoinColumn(name = "fk_empresas_id")
    private Empresa empresa;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_usuarios_id")
    @JoinColumn(name = "fk_usuarios_id")
    private UsuarioShort usuario;
    
    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_perfis_id")
    @JoinColumn(name = "fk_perfis_id")
    private Perfil perfil;
    
}
