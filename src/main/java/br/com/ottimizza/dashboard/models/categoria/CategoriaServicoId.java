package br.com.ottimizza.dashboard.models.categoria;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class CategoriaServicoId implements Serializable{
    
    @Column(name = "fk_categoria_id")
    private Long fkCategoriaId;
            
    @Column(name = "fk_servico_id")
    private Long fkServicoId;
    
}
