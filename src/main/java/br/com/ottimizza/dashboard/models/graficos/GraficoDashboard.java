package br.com.ottimizza.dashboard.models.graficos;

import java.io.Serializable;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class GraficoDashboard implements Serializable {
    
    private BigInteger id;
    private String nomeIndicador;
    private String nomeGrafico;
    private Long abertoNoPrazo;
    private Long abertoAtrasado;
    private Long encerradoNoPrazo;
    private Long encerradoAtrasado;
}