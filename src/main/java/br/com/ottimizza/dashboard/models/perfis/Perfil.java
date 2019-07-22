package br.com.ottimizza.dashboard.models.perfis;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "ot_perfis")
public class Perfil implements Serializable {

    @Id
    @Getter
    @Setter
    @SequenceGenerator(name = "perfis_sequence", sequenceName = "perfis_sequence_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfis_sequence")
    private BigInteger id;

    @Getter
    @Setter
    @Column(name = "descricao", nullable = false)
    private String descricao;

}
