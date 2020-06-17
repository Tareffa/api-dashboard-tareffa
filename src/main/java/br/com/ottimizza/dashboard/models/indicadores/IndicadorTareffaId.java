package br.com.ottimizza.dashboard.models.indicadores;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor @NoArgsConstructor
@Embeddable
public class IndicadorTareffaId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long usuarioCliente;
	
	private Long contador;
	
	private Long empresa;
	
	private Long servicoProgramado;
	
	private Long contabilidade;
	
	private Long documento;
}
