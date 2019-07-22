package br.com.ottimizza.dashboard.models.departamentos;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class DepartamentoShort implements Serializable {

    @Id
    @Getter
    @Setter
    @SequenceGenerator(name = "departamentos_sequence", sequenceName = "departamentos_id_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departamentos_sequence")
    private Long id;

    @Getter
    @Setter
    @Column(name = "descricao", nullable = true)
    private String descricao;

}
