package br.com.ottimizza.dashboard.models.caracteristica;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_caracteristicas_servico_filter")
public class CaracteristicaServicoView implements Serializable{

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
    @Column(name = "fk_contabilidades_id", nullable = true)
    private Long contabilidade;
    
    @Getter
    @Setter
    @Column(name = "fk_classificacao_id", nullable = true)
    private Long classificacao;
    
    @Getter
    @Setter
    @Column(name = "fk_servico_id", nullable = true)
    private Long servico;

    @Getter
    @Setter
    @Column(name = "fk_regra_caracteristica_servico_id", nullable = true)
    private Long regraCaracteristica;

    @Getter
    @Setter
    @Column(name = "cadastrado", insertable = false, updatable = false)
    private Boolean cadastrado;
    
}
