package br.com.ottimizza.dashboard.models.caracteristica;

import br.com.ottimizza.dashboard.models.Classificacao;
import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_caracteristicas")
public class Caracteristica implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Getter
    @Setter
    @Column(name = "descricao")
    private String descricao;
    
    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_contabilidades_id", nullable = true)
    private ContabilidadeShort contabilidade;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_classificacao_id", nullable = true)
    private Classificacao classificacao;

}
