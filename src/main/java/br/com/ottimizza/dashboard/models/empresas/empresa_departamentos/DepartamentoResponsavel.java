package br.com.ottimizza.dashboard.models.empresas.empresa_departamentos;

import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioShort;

import java.io.Serializable;
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
@Table(name = "ot_empresas_gruposervicos")
public class DepartamentoResponsavel implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_gruposervicos_id", nullable = false)
    private DepartamentoShort departamento;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "fk_usuarios_id", nullable = true)
    private UsuarioShort usuario;

}
