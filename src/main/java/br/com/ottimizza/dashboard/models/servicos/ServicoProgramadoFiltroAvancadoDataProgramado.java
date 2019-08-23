package br.com.ottimizza.dashboard.models.servicos;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ServicoProgramadoFiltroAvancadoDataProgramado implements Serializable{
    
    @Getter @Setter
    private Date dataProgramada;
    
    @Getter @Setter
    private ServicoProgramadoFiltroAvancado filtroAvancado;
}
