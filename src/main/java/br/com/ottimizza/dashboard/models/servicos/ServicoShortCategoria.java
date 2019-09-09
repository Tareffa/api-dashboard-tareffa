package br.com.ottimizza.dashboard.models.servicos;


import br.com.ottimizza.dashboard.models.GuiaShort;
import br.com.ottimizza.dashboard.models.categoria.Categoria;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ot_servicos")
@AllArgsConstructor @NoArgsConstructor
public class ServicoShortCategoria implements Serializable {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter @Setter
    @Column(name = "nome", nullable = true)
    private String nome = "";
    
    @Getter @Setter
    @Column(name = "is_ativo")
    private Boolean ativo = false;
    
    @Getter @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_gruposervico", nullable = true)
    private DepartamentoShort grupoServico;

    @Getter @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_guiaerp_id", nullable = true)
    private GuiaShort baixaPorGuia;
    
}
