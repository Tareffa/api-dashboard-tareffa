package br.com.ottimizza.dashboard.models.usuarios;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_usuarios")
public class UsuarioShortComGerente implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Getter
    @Setter
    @Column(name = "nome", nullable = false)
    private String nome;

    @Getter
    @Setter
    @Column(name = "email", nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(name = "tipo", nullable = false)
    private int tipoUsuario;

    @Getter
    @Setter
    @Column(name = "nivel", nullable = false)
    private int nivelUsuario;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_usuarios_id", referencedColumnName = "id", nullable = true)
    private UsuarioShort gerente;
}