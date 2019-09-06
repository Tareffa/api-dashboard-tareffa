package br.com.ottimizza.dashboard.models.servicos.servico_categorias;

import br.com.ottimizza.dashboard.models.categoria.CategoriaShort;
import br.com.ottimizza.dashboard.models.servicos.Servico;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ot_servicos_categorias")
@AllArgsConstructor @NoArgsConstructor
public class ServicoCategoria implements Serializable {

    @EmbeddedId
    @Getter @Setter
    private ServicoCategoriaID id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoriaId")
    @Getter @Setter
    private CategoriaShort categoria;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("servicoId")
    @Getter @Setter
    private Servico servico;
    
}
