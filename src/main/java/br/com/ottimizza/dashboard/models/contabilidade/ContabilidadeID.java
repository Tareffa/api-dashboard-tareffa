package br.com.ottimizza.dashboard.models.contabilidade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "ot_contabilidades")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor @NoArgsConstructor
public class ContabilidadeID implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter @Setter
    @Column(name = "id", nullable = false)
    private Long id;
    
}
