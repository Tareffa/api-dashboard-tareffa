package br.com.ottimizza.dashboard.models;

import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "ot_classificacoes")
public class Classificacao implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @Column(name = "descricao")
    private String descricao;
    
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_contabilidades_id", nullable = false)
    private ContabilidadeShort contabilidade;

    @Override
    public boolean equals(Object obj) {
        Classificacao classificacao = (Classificacao) obj;
        return Objects.equals(classificacao.id, id);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }
}