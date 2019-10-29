package br.com.ottimizza.dashboard.models.usuarios;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor @AllArgsConstructor
public class UsuarioDashboard implements Serializable {
    
    private Long id;
    private String nome;
    private String urlFoto;
    private Long abertoNoPrazo;
    private Long abertoAtrasado;
    private Long encerradoNoPrazo;
    private Long encerradoAtrasado;
}