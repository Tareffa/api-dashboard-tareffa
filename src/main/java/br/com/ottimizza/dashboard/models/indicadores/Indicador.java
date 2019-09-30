package br.com.ottimizza.dashboard.models.indicadores;

import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "ot_indicador")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Indicador implements Serializable{
    
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    
    @Column(name = "descricao")
    private String descricao;
    
    @ManyToOne
    @JoinColumn(name = "fk_contabilidade_id", nullable = false)
    private ContabilidadeShort contabilidade;
    
}
