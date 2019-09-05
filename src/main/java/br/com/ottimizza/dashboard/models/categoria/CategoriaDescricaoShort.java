package br.com.ottimizza.dashboard.models.categoria;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
public class CategoriaDescricaoShort implements Serializable{
    
    @Getter @Setter
    private Long id;
    
    @Getter @Setter
    private String descricao = "";
    
}
