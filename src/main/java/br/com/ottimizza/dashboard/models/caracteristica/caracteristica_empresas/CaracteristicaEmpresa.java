package br.com.ottimizza.dashboard.models.caracteristica.caracteristica_empresas;

import br.com.ottimizza.dashboard.models.caracteristica.Caracteristica;
import br.com.ottimizza.dashboard.models.empresas.Empresa;
import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_caracteristicas_empresas")
public class CaracteristicaEmpresa implements Serializable{
    
    @EmbeddedId
    private CaracteristicaEmpresaID id;
    
    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_caracteristicas_id")
    @JoinColumn(name = "fk_caracteristicas_id", insertable = false, updatable = false)
    private Caracteristica caracteristica;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_empresas_id")
    @JoinColumn(name = "fk_empresas_id", insertable = false, updatable = false)
    private Empresa empresa;
    
}
