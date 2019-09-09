package br.com.ottimizza.dashboard.models.categoria.categoria_servico;

import br.com.ottimizza.dashboard.models.categoria.Categoria;
import br.com.ottimizza.dashboard.models.servicos.Servico;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "ot_servicos_categorias")
@AllArgsConstructor @NoArgsConstructor
public class ServicoCategoria implements Serializable {
    
    @EmbeddedId
    private ServicoCategoriaID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_categoria_id")
    private Categoria categoria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_servico_id")
    private Servico servico;
    
}
