package br.com.ottimizza.dashboard.models.empresas;

import br.com.ottimizza.dashboard.models.Classificacao;
import br.com.ottimizza.dashboard.models.RegimeTributario;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioShort;
import br.com.ottimizza.dashboard.models.Cnae;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_empresas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Empresa implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "is_ativo", nullable = false)
    private boolean ativo;

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
    @Column(name = "inscricao_estadual", nullable = true)
    private String inscricaoEstadual;

    @Getter
    @Setter
    @Column(name = "codigo_erp", nullable = true)
    private String codigoErp;

    @Getter
    @Setter
    @Column(name = "situacao", nullable = true)
    private Short situacao;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "inicio_validade_classificacao", nullable = true)
    private Date inicioValidadeClassificacao;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "termino_validade_classificacao", nullable = true)
    private Date terminoValidadeClassificacao;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_usuarios_id", nullable = true)
    private UsuarioShort createdBy;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_empresa_contabilidade_id", nullable = false)
    private EmpresaContabilidade empresaContabilidade;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_regimes_tributarios_id", nullable = false)
    private RegimeTributario regimeTributario;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_classificacoes_id", nullable = true)
    private Classificacao classificacao;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_cnae_primario", nullable = true)
    private Cnae cnaePrimario;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "empresa_cnaes_secundarios", joinColumns = {
        @JoinColumn(name = "fk_empresas_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "fk_cnaes_id", referencedColumnName = "id")
    })
    private List<Cnae> cnaesSecundarios;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = true)
    private Date createdAt;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Getter
    @Setter
    @JsonIgnore
    @Column(name = "full_description", nullable = true)
    private String fullDescription;

    @Getter
    @Setter
    @Transient
    private Set<Classificacao> classificacoes;

    @PreUpdate
    @PrePersist
    public void updateDescription() {
        StringBuilder fd = new StringBuilder();

        fd.append(codigoErp);
        if (nomeResumido != null && !nomeResumido.equals("")) {
            fd.append("--").append(nomeResumido);
        }
        if (razaoSocial != null && !razaoSocial.equals("")) {
            fd.append("--").append(razaoSocial);
        }
        fullDescription = fd.toString().toUpperCase();

        updatedAt = new Date();
        if (createdAt == null) {
            createdAt = new Date();
        }
    }

}
