package br.com.ottimizza.dashboard.models.departamentos;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_gruposervicos")
public class Departamento implements Serializable {

    @Id
    @Getter
    @Setter
    @SequenceGenerator(name = "departamentos_sequence", sequenceName = "departamentos_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamentos_sequence")
    private Long id;

    @Getter
    @Setter
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Getter
    @Setter
    @Column(name = "comunicacao_contact_id", nullable = true)
    private String comunicacaoContactId;

    @Getter
    @Setter
    @Column(name = "tipo_contabilidade", nullable = true)
    private Boolean tipoContabilidade;

    @Getter
    @Setter
    @Column(name = "tipo_cliente", nullable = true)
    private Boolean tipoCliente;
    
    @Getter
    @Setter
    @Column(name = "is_grupo", nullable = true)
    private Boolean isGrupo;

    @Getter
    @Setter
    @Column(name = "usar_comunicacao", nullable = true)
    private Boolean usarComunicacao;
    
    @Getter
    @Setter
    @Column(name = "tag_description", nullable = true)
    private String tagDescription;
    
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_usuarios_id", nullable = true)
    private Usuario gerente;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_contabilidades_id", nullable = false)
    private ContabilidadeShort contabilidade;

}
