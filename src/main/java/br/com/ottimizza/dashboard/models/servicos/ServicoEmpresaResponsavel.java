package br.com.ottimizza.dashboard.models.servicos;

import br.com.ottimizza.dashboard.models.empresas.EmpresaResponsavelDataVencimento;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ServicoEmpresaResponsavel implements Serializable{
    
    @Getter @Setter
    private Long id;
    
    @Getter @Setter
    private String nomeServico;
    
//    @Getter @Setter
//    private List<EmpresaResponsavelDataVencimento> lista;
    
}
