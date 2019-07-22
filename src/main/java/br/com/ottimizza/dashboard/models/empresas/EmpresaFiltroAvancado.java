package br.com.ottimizza.dashboard.models.empresas;

import br.com.ottimizza.dashboard.models.Classificacao;
import br.com.ottimizza.dashboard.models.Cnae;
import br.com.ottimizza.dashboard.models.RegimeTributario;
import br.com.ottimizza.dashboard.models.caracteristica.Caracteristica;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioShort;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class EmpresaFiltroAvancado implements Serializable {

    @Getter
    @Setter
    private String codigoERP;

    @Getter
    @Setter
    private String razaoSocial;

    @Getter
    @Setter
    private String cnpj;

    @Getter
    @Setter
    private Boolean matriz;

    @Getter
    @Setter
    private Boolean filial;

    @Getter
    @Setter
    private Short situacao;

    @Getter
    @Setter
    private List<Classificacao> classificacao;

    @Getter
    @Setter
    private List<RegimeTributario> regimeTributario;

    @Getter
    @Setter
    private List<Cnae> cnaePrincipal;
    
    @Getter
    @Setter
    private DepartamentoShort departamento;

    @Getter
    @Setter
    private UsuarioShort responsavel;

    @Getter
    @Setter
    private List<Caracteristica> caracteristicas;

    @Getter
    @Setter
    private List<String> codigosERP;

}
