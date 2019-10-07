package br.com.ottimizza.dashboard.models.servicos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ServicoAgrupadoProgramado implements Serializable{
    
    
    private Long id;
    
    private String nomeServico;
    
    @Temporal(TemporalType.DATE)
    private Date dataProgramada;
    
    private Long servicosProgramadosContagem;
    
}
