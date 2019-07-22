package br.com.ottimizza.dashboard.models.empresas;

import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import java.io.Serializable;
import java.util.Date;
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
@Table(name = "ot_empresa_contabilidade")
public class EmpresaContabilidade implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column(name = "cnpj")
    private String cnpj;

    @Getter
    @Setter
    @Column(name = "is_matriz")
    private boolean matriz;

    @Getter
    @Setter
    @Column(name = "inicio_prestacao_servicos", nullable = true)
    public Date inicioPrestacaoServicos;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_contabilidades_id", referencedColumnName = "id", nullable = false)
    private ContabilidadeShort contabilidade;

    @Getter
    @Setter
    @Column(name = "comunicacao_organization_id", nullable = true)
    private String comunicacaoOrganizationId;

}
