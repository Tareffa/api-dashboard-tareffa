package br.com.ottimizza.dashboard.models.usuarios;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UsuarioImagens implements Serializable{
    
    String urlFotoUsuario;
    String urlLogoContabilidade;   
}