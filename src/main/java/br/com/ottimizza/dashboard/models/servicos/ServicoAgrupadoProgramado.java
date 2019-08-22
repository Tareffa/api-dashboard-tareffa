package br.com.ottimizza.dashboard.models.servicos;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ServicoAgrupadoProgramado implements Serializable{
    
    @Getter @Setter
    private Long id;
    
    @Getter @Setter
    private String nomeServico;
    
    @Getter @Setter
    @Temporal(TemporalType.DATE)
    private Date dataProgramada;
    
    @Getter @Setter
    private Long servicosProgramadosContagem;
    
}
