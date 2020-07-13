package br.com.ottimizza.dashboard.models.indicadores;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "view_tareffa_indicadores_site")
@Data
public class IndicadorTareffa implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IndicadorTareffaId id;
	
	@Column(name = "usuarios_clientes")
	private Long usuarioCliente;
	
	@Column(name = "contadores")
	private Long contador;
	
	@Column(name = "empresas")
	private Long empresa;
	
	@Column(name = "servicos_programados")
	private Long servicoProgramado;
	
	@Column(name = "contabilidades")
	private Long contabilidade;
	
	@Column(name = "documentos")
	private Long documento;
	
	public IndicadorTareffa(Long usuarioCliente, Long contador, Long empresa, Long servicoProgramado,
			Long contabilidade, Long documento) {
		super();
		this.usuarioCliente = usuarioCliente;
		this.contador = contador;
		this.empresa = empresa;
		this.servicoProgramado = servicoProgramado;
		this.contabilidade = contabilidade;
		this.documento = documento;
	}
	
}
