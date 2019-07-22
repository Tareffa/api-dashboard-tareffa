package br.com.ottimizza.dashboard.models.empresas;

import br.com.ottimizza.dashboard.models.perfis.Perfil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_empresas")
public class EmpresaShortWithClassificacaoPerfil implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_empresa_contabilidade_id", nullable = false)
    private EmpresaContabilidadeShort empresaContabilidade;

    @Getter
    @Setter
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Getter
    @Setter
    @Column(name = "nome_resumido", nullable = true)
    private String nomeResumido;

    @Getter
    @Setter
    @Column(name = "codigo_erp", nullable = true)
    private String codigoErp;

    @Getter
    @Setter
    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfis;

}
