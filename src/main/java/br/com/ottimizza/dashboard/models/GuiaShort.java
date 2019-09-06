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
@Table( name = "ot_guias_erp")
@AllArgsConstructor @NoArgsConstructor
public class GuiaShort implements Serializable {
    
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @Getter @Setter
    @Column(name = "descricao", nullable = false)
    private String descricao;
    
    
}
