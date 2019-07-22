/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ottimizza.dashboard.models.empresas.empresa_servicos;

import br.com.ottimizza.dashboard.models.empresas.Empresa;
import br.com.ottimizza.dashboard.models.empresas.empresa_servicos.EmpresaServicoID;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_empresas_servicos")
public class EmpresaServicoCustomizado implements Serializable {

    @Getter
    @Setter
    @EmbeddedId
    private EmpresaServicoID id;

    @Getter
    @Setter
    @ManyToOne
    @MapsId("fk_empresas_id")
    @JoinColumn(name = "fk_empresas_id", insertable = false, updatable = false)
    private Empresa empresa;
    
    @Getter
    @Setter
    @Column(name = "vencimento_alternativo")
    private Integer vencimentoAlternativo = 0;

    @Getter
    @Setter
    @Column(name = "ativo", nullable = true)
    private Boolean ativo = true;

    @Getter
    @Setter
    @Column(name = "justificativa", nullable = true)
    private String justificativa;
    
}
