package br.com.ottimizza.dashboard.models.servicos;

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
@Getter @Setter
@Table(name = "ot_servicos")
@NoArgsConstructor @AllArgsConstructor
public class ServicoShort implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "nome", nullable = true)
    private String nome = "";

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_contabilidades_id", nullable = true)
    private ContabilidadeShort contabilidade;
    
    @Column(name = "permite_baixa_manual", nullable = true)
    private Boolean permiteBaixaManual;
}