package br.com.ottimizza.dashboard.models.servicos;

import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
import br.com.ottimizza.dashboard.models.empresas.EmpresaShort;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ServicoProgramadoFiltroAvancado implements Serializable {

    @Getter
    @Setter
    private Date dataProgramadaInicio;
    @Getter
    @Setter
    private Date dataProgramadaTermino;

    @Getter
    @Setter
    private Date dataVencimentoInicio;
    @Getter
    @Setter
    private Date dataVencimentoTermino;

    @Getter
    @Setter
    private DepartamentoShort departamento;
    @Getter
    @Setter
    private EmpresaShort empresa;
    @Getter
    @Setter
    private ServicoShort servico;
    @Getter
    @Setter
    private Usuario usuario;

    @Getter
    @Setter
    private String competencia;

    @Getter
    @Setter
    private Short status;
    
    @Getter
    @Setter
    private Short situacao;
    
    @Getter
    @Setter
    private List<Short> prazo;
    
    @Getter
    @Setter
    private Short tipoBaixa;

    @Getter
    @Setter
    private Boolean servicosAtivos;
    
}
