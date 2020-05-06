package br.com.ottimizza.dashboard.models.usuarios.usuarios_unidade_negocio;

import br.com.ottimizza.dashboard.models.caracteristica.Caracteristica;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioShort;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ot_usuario_unidade_negocio")
@AllArgsConstructor @NoArgsConstructor
public class UsuarioUnidadeNegocio implements Serializable{
    
    @EmbeddedId
    private UsuarioUnidadeNegocioID id;
    
    @ManyToOne
    @MapsId("fk_usuario_id")
    @JoinColumn(name = "fk_usuario_id") 
    private UsuarioShort usuario;

    @ManyToOne
    @MapsId("fk_unidade_negocio_id")
    @JoinColumn(name = "fk_unidade_negocio_id")
    private Caracteristica unidadeNegocio;
}