package br.com.ottimizza.dashboard.models.servicos;

import br.com.ottimizza.dashboard.models.Guia;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
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
@Table(name = "ot_servicos")
public class ServicoShortComDepartamentoEGuiaERP implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column(name = "nome", nullable = true)
    private String nome = "";

    @Getter
    @Setter
    @Column(name = "is_ativo")
    private Boolean ativo = false;

    @Getter
    @Setter
    @Column(name = "permite_baixa_manual", nullable = true)
    private Boolean permiteBaixaManual;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_gruposervico", nullable = true)
    private DepartamentoShort departamento;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_guiaerp_id", nullable = true)
    private Guia guiaERP;
}