package br.com.ottimizza.dashboard.models.usuarios.usuarios_empresas;

import br.com.ottimizza.dashboard.models.usuarios.UsuarioShort;
import br.com.ottimizza.dashboard.models.empresas.EmpresaShort;
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
@Table(name = "ot_usuarios_empresas")
public class UsuarioEmpresa implements Serializable {

    @Getter
    @Setter
    @EmbeddedId
    private UsuarioEmpresaID id;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_usuarios_id")
    @JoinColumn(name = "fk_usuarios_id") //, insertable = false, updatable = false
    private UsuarioShort usuario;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_empresas_id")
    @JoinColumn(name = "fk_empresas_id") // , insertable = false, updatable = false
    private EmpresaShort empresa;
}