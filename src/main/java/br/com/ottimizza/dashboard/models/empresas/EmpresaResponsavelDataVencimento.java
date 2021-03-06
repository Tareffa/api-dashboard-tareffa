package br.com.ottimizza.dashboard.models.empresas;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class EmpresaResponsavelDataVencimento implements Serializable{
    
    @Getter @Setter
    private String nomeResponsavel;
    
    @Getter @Setter
    private String fotoResponsavel;
    
    @Getter @Setter
    private String codigoErpEmpresa;
    
    @Getter @Setter
    private String razaoSocialEmpresa;
    
    @Getter @Setter
    private Long servicoProgramadoId;
    
    @Getter @Setter
    @Temporal(TemporalType.DATE)
    private Date dataTermino;
    
}
