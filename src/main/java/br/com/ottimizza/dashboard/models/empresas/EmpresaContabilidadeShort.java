package br.com.ottimizza.dashboard.models.empresas;

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
@Table(name = "ot_empresa_contabilidade")
public class EmpresaContabilidadeShort implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column(name = "is_matriz")
    private Boolean matriz;

    @Getter
    @Setter
    @Column(name = "cnpj")
    private String cnpj;

    @Getter
    @Setter
    @Column(name = "comunicacao_organization_id", nullable = true)
    private String comunicacaoOrganizationId;

}
