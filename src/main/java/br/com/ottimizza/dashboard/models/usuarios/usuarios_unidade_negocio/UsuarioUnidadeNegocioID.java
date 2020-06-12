package br.com.ottimizza.dashboard.models.usuarios.usuarios_unidade_negocio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor @NoArgsConstructor
public class UsuarioUnidadeNegocioID implements Serializable{

    @Column(name = "fk_usuario_id")
    private Long usuarioId;

    //Grupo de características 04 - unidade de negócio
    @Column(name = "fk_unidade_negocio_id")
    private Long unidadeNegocioId;
}