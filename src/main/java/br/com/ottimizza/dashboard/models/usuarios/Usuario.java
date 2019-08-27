package br.com.ottimizza.dashboard.models.usuarios;

import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
import br.com.ottimizza.dashboard.models.empresas.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "ot_usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario implements Serializable {

    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter @Setter
    @Column(name = "nome", nullable = false)
    private String nome;

    @Getter @Setter
    @Column(name = "email", nullable = false)
    private String email;

    @Getter @Setter
    @Column(name = "senha", nullable = true)
    private String senha;

    @Getter @Setter
    @Column(name = "tipo", nullable = false)
    private int tipoUsuario;

    @Getter @Setter
    @Column(name = "nivel", nullable = false)
    private int nivelUsuario;

    @Getter @Setter
    @Column(name = "situacao", nullable = true)
    private Short situacao = 1;

    @Getter @Setter
    @Column(name = "tag_description", nullable = true)
    private String tagDescription;

    @Getter @Setter
    @Deprecated
    @Column(name = "email_altera_remetente")
    private Boolean permiteAlterarRemetente = false;

    @Getter @Setter
    @Column(name = "email_recebe_circular")
    private Boolean recebeEmailCircular = false;

    @Getter @Setter
    @Column(name = "comunicacao_envia_circular")
    private Boolean enviaCircular = false;
    
    @Getter @Setter
    @Column(name = "enxerga_como_cliente")
    private Boolean enxergaComoCliente = false;
    
    @Getter @Setter
    @Column(name = "consulta_gestao_servicos")
    private Boolean consultaGestaoServicos = false;

    @Getter @Setter
    @Column(name = "is_notified")
    private Boolean notificaUsuario = true;

    @Getter @Setter
    @Column(name = "comunicacao_contact_id", nullable = true)
    private String comunicacaoContactId;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "fk_usuarios_id", referencedColumnName = "id", nullable = true)
    private UsuarioImpl1 gerente;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "fk_contabilidades_id", referencedColumnName = "id", nullable = false)
    private ContabilidadeShort contabilidade;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "fk_departamentos_id", referencedColumnName = "id", nullable = true)
    private DepartamentoShort departamento;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "fk_empresas_id", referencedColumnName = "id", nullable = true)
    private Empresa empresa;
    
    @Getter @Setter
    @Column(name = "url_foto", nullable = true, columnDefinition = "text")
    private String urlFoto;
}