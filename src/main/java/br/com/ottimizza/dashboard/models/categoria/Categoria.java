package br.com.ottimizza.dashboard.models.categoria;

import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeID;
import br.com.ottimizza.dashboard.models.servicos.ServicoShortCategoria;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ot_categorias")
@AllArgsConstructor @NoArgsConstructor
public class Categoria implements Serializable{
    
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter @Setter
    @Column(name = "descricao")
    private String descricao = "";
    
    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "fk_contabilidade_id", referencedColumnName = "id")
    private ContabilidadeID contabilidade;
    
    @Getter @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "ot_servicos_categorias", joinColumns = {
        @JoinColumn(name = "fk_categoria_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "fk_servico_id", referencedColumnName = "id")
    })
    private Set<ServicoShortCategoria> servicos = new HashSet<>();
    
}
