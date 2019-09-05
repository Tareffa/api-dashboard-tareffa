package br.com.ottimizza.dashboard.models.categoria;

import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeID;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ot_categorias")
@AllArgsConstructor @NoArgsConstructor
public class CategoriaShort implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter
    private Long id;

    @Column(name = "descricao")
    @Getter @Setter
    private String descricao = "";
    
    @ManyToOne
    @JoinColumn(name = "fk_contabilidade_id", referencedColumnName = "id")
    @Getter @Setter
    private ContabilidadeID contabilidade;
    
}
