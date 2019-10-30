package br.com.ottimizza.dashboard.models.graficos;

import java.io.Serializable;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor @AllArgsConstructor
public class GraficoDashboard implements Serializable {
    
    private BigInteger id;
    private String nome;
    private Long abertoNoPrazo;
    private Long abertoAtrasado;
    private Long encerradoNoPrazo;
    private Long encerradoAtrasado;
}