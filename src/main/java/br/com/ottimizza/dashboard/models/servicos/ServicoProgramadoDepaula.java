package br.com.ottimizza.dashboard.models.servicos;

import br.com.ottimizza.dashboard.models.empresas.EmpresaShort;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_servicos_programados")
public class ServicoProgramadoDepaula implements Serializable {

    @Id
    @Getter
    @Setter
    @SequenceGenerator(name = "servicosprogramados_seq", sequenceName = "servicosprogramados_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicosprogramados_seq")
    private Long id;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_empresas_id", nullable = true)
    private EmpresaShort cliente;

    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_servicos_id", nullable = false)
    private Servico servico;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_usuarios_id", nullable = true)
    private Usuario alocadoPara;

    @Getter
    @Setter
    @Column(name = "status")
    private Short status;

    @Getter
    @Setter
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Getter
    @Setter
    @Column(name = "referencia")
    private String referencia;

    @Getter
    @Setter
    @Column(name = "competencia")
    private String competencia;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "data_programada_inicio")
    private Date dataProgramadaInicio;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "data_programada_entrega")
    private Date dataProgramadaEntrega;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "data_vencimento")
    private Date dataVencimento;

    @Getter
    @Setter
    @Temporal(TemporalType.DATE)
    @Column(name = "data_termino")
    private Date dataTermino;

    @Getter
    @Setter
    @Column(name = "tipo_geracao")
    private Short tipoGeracao = 1;

    @Getter
    @Setter
    @Column(name = "tipo_baixa")
    private Short tipoBaixa = 0;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Getter
    @Setter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @PreUpdate
    @PrePersist
    public void updateTimestamps() {
        this.updatedAt = new Date();
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }

}
