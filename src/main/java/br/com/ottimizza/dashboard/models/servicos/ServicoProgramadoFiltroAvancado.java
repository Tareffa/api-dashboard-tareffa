package br.com.ottimizza.dashboard.models.servicos;

import br.com.ottimizza.dashboard.models.caracteristica.CaracteristicaShort;
import br.com.ottimizza.dashboard.models.categoria.CategoriaShort;
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

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ServicoProgramadoFiltroAvancado implements Serializable {
    
    private Date dataProgramadaInicio;
    private Date dataProgramadaTermino;
    
    private Date dataVencimentoInicio;
    private Date dataVencimentoTermino;
    
    private List<DepartamentoShort> departamento;
    private List<EmpresaShort> empresa;
    private List<ServicoShort> servico;
    private List<Usuario> usuario;
    
    private String competencia;
    
    private Short status;
    
    private Short situacao;
    
    private List<Short> prazo;
    
    private Short tipoBaixa;
    
    private Boolean servicosAtivos;
    
    private CategoriaShort categoria;
    
    private CaracteristicaShort caracteristica;
}