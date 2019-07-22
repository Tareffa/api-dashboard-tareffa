package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import java.util.Objects;
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
@Table(name = "ot_regimes_tributarios")
public class RegimeTributario implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "descricao", nullable = false)
    private String descricao;
    
    public boolean equals(RegimeTributario rt) {
        return (Objects.equals(this.id, rt.getId()) && this.descricao.toUpperCase().equals(rt.getDescricao().toUpperCase())); 
    }
    
}
