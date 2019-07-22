package br.com.ottimizza.dashboard.models.usuarios;

import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import br.com.ottimizza.dashboard.models.empresas.Empresa;
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
@Table(name = "ot_usuarios")
public class UsuarioComSubordidados implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "nome", nullable = false)
    private String nome;

    @Getter
    @Setter
    @Column(name = "email", nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(name = "senha", nullable = true)
    private String senha;

    @Getter
    @Setter
    @Column(name = "tipo", nullable = false)
    private int tipoUsuario;

    @Getter
    @Setter
    @Column(name = "nivel", nullable = false)
    private int nivelUsuario;

    @Getter
    @Setter
    @Column(name = "email_altera_remetente")
    private Boolean permiteAlterarRemetente;

    @Getter
    @Setter
    @Column(name = "email_copia_externa")
    private Boolean recebeEmailCopiaExterna;

    @Getter
    @Setter
    @Column(name = "email_recebe_circular")
    private Boolean recebeEmailCircular = false;

    @Getter
    @Setter
    @Column(name = "comunicacao_envia_circular")
    private Boolean enviaCircular = false;
    
    @Getter
    @Setter
    @Column(name = "enxerga_como_cliente")
    private Boolean enxergaComoCliente = false;

    @Getter
    @Setter
    @Column(name = "comunicacao_contact_id", nullable = true)
    private String comunicacaoContactId;
    
    @Getter
    @Setter
    @Column(name = "consulta_gestao_servicos")
    private Boolean consultaGestaoServicos = false;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_usuarios_id", referencedColumnName = "id", nullable = true)
    private UsuarioImpl1 gerente;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_contabilidades_id", referencedColumnName = "id", nullable = false)
    private ContabilidadeShort contabilidade;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_empresas_id", referencedColumnName = "id", nullable = true)
    private Empresa empresa;

    @Getter
    @Setter
    @Fetch(FetchMode.SELECT)
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_usuarios_id")
    private List<UsuarioImpl1> usuarios;
}
