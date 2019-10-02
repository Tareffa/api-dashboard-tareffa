package br.com.ottimizza.dashboard.models.graficos;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "ot_grafico")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class GraficoShort implements Serializable{
    
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private BigInteger id;
    
    @Column(name = "nome_grafico")
    private String nomeGrafico;
    
}
