package br.com.ottimizza.dashboard.models;

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
@Table( name = "ot_guias_erp")
public class Guia implements Serializable {
    
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @Getter
    @Setter
    @Column(name = "descricao", nullable = false)
    private String descricao;
    
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_contabilidades_id", nullable = true)
    private ContabilidadeShort contabildade;
}
