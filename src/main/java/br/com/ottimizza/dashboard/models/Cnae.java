package br.com.ottimizza.dashboard.models;

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
@Table(name = "ot_cnaes")
public class Cnae implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Getter
    @Setter
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Getter
    @Setter
    @Column(name = "codigo_divisao", nullable = false)
    private String codigoDivisao;

    @Getter
    @Setter
    @Column(name = "descricao_divisao", nullable = false)
    private String descricaoDivisao;

    @Override
    public boolean equals(Object obj) {
        return (this.codigo.equals(((Cnae) obj).codigo));
    }

}
