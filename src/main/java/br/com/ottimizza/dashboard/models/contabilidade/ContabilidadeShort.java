package br.com.ottimizza.dashboard.models.contabilidade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "ot_contabilidades")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ContabilidadeShort implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "razao_social", nullable = false)
    private String razaoSocial;

    @Getter
    @Setter
    @Column(name = "nome_resumido", nullable = false)
    private String nomeResumido;

    @Getter
    @Setter
    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Getter
    @Setter
    @Column(name = "token", nullable = true)
    private String token;

    @Getter
    @Setter
    @Column(name = "email_valida_codigoerp", nullable = false)
    private Boolean validaAnexosCodigoErp;

    @Getter
    @Setter
    @Column(name = "comunicacao_organization_id", nullable = true)
    private String comunicacaoOrganizationId;
    
    @Getter
    @Setter
    @Column(name = "url_logotipo", columnDefinition = "TEXT", nullable = true)
    private String urlLogotipo;

}
