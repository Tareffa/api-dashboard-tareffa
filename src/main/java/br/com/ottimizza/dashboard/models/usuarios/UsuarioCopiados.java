package br.com.ottimizza.dashboard.models.usuarios;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_usuarios")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UsuarioCopiados implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "ot_usuarios_copiados", joinColumns = {
        @JoinColumn(name = "fk_usuarios_from", referencedColumnName = "id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "fk_usuarios_to", referencedColumnName = "id")
    })
    private Set<Usuario> usuarioss;
}