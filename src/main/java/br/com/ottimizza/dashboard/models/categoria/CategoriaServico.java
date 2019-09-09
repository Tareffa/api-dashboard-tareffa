package br.com.ottimizza.dashboard.models.categoria;

import br.com.ottimizza.dashboard.models.servicos.Servico;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "ot_servicos_categorias")
public class CategoriaServico implements Serializable{
    
    @EmbeddedId
    private CategoriaServicoId id;
    
    @ManyToOne
    @JoinColumn(name = "fk_categoria_id")
    private Categoria categoria;
    
    @ManyToOne
    @JoinColumn(name = "fk_servico_id")
    private Servico servico;
    
}
