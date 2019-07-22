package br.com.ottimizza.dashboard.models.servicos;

import br.com.ottimizza.dashboard.models.empresas.EmpresaShort;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioImpl1;
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
public class ServicoProgramadoImpl1 implements Serializable {

    @Id
    @Getter
    @Setter
    @SequenceGenerator(name = "servicosprogramados_seq", sequenceName = "servicosprogramados_id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicosprogramados_seq")
    private Long id;

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
    @Column(name = "status")
    private short status;

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

    //<editor-fold defaultstate="collapsed" desc="fk_usuarios_id">
    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_usuarios_id", nullable = true)
    private UsuarioImpl1 alocadoPara;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="fk_empresas_id">
    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_empresas_id", nullable = true)
    private EmpresaShort cliente;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="fk_servicos_id">
    @Getter
    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "fk_servicos_id", nullable = false)
    private ServicoShort servico;
    //</editor-fold>

    @PreUpdate
    @PrePersist
    public void updateTimestamps() {
        this.updatedAt = new Date();
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }
    }
}
