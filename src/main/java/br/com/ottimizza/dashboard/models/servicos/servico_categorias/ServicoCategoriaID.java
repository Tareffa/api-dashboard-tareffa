package br.com.ottimizza.dashboard.models.servicos.servico_categorias;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor @NoArgsConstructor
public class ServicoCategoriaID implements Serializable{
    
    @Getter @Setter
    @Column(name = "fk_empresa_id")
    private Long fkCategoriaId;

    @Getter @Setter
    @Column(name = "fk_servico_id")
    private Long fkServicoId;
    
}
