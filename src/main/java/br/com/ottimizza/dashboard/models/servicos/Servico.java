package br.com.ottimizza.dashboard.models.servicos;

import br.com.ottimizza.dashboard.models.Classificacao;
import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import br.com.ottimizza.dashboard.models.departamentos.Departamento;
import br.com.ottimizza.dashboard.models.perfis.Perfil;
import br.com.ottimizza.dashboard.models.Guia;
import br.com.ottimizza.dashboard.models.categoria.Categoria;
import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ot_servicos")
public class Servico implements Serializable {

    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_guiaerp_id", nullable = true)
    private Guia baixaPorGuia;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_contabilidades_id", nullable = true)
    private ContabilidadeShort contabilidade;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_gruposervico", nullable = true)
    private Departamento grupoServico;

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_servicopai", nullable = true, insertable = true)
    private Servico servicoPai;

    @Getter
    @Setter
    @Column(name = "nome", nullable = true)
    private String nome = "";

    @Getter
    @Setter
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_perfis_id", nullable = true, insertable = true)
    private Perfil perfil;

    @Getter
    @Setter
    @Column(name = "comunicacao_assunto", nullable = true)
    private String comunicacaoAssunto = "";

    @Getter
    @Setter
    @Column(name = "comunicacao_mensagem", columnDefinition = "TEXT", nullable = true)
    private String comunicacaoMensagem = "";

    @Getter
    @Setter
    @Column(name = "dias_antecedencia_inicio", nullable = true)
    private int diasAntecedenciaInicio = 0;

    @Getter
    @Setter
    @Column(name = "dias_antecedencia_entrega", nullable = true)
    private int diasAntecedenciaEntrega = 0;

    @Getter
    @Setter
    @Column(name = "dia_vencimento", nullable = true)
    private int diaVencimento = 0;

    @Getter
    @Setter
    @Column(name = "baixa_competencias_ateriores")
    private Boolean baixaCompetenciasAnteriores = false;

    @Getter
    @Setter
    @Column(name = "is_servicofaturado")
    private boolean servicoFaturado = false;

    @Getter
    @Setter
    @Column(name = "is_comunicacliente")
    private boolean comunicaCliente = false;

    @Getter
    @Setter
    @Column(name = "is_servicoavulso")
    private boolean servicoAvulso = false;

    @Getter
    @Setter
    @Column(name = "is_diautil")
    private boolean diaUtil = false;

    @Getter
    @Setter
    @Column(name = "controla_vencimento", nullable = true)
    private Boolean controlaVencimento = false;

    @Getter
    @Setter
    @Column(name = "matriz_filial", nullable = true)
    private int matrizFilial = 0;

    @Getter
    @Setter
    @Column(name = "competencia", nullable = true)
    private int competencia = 0;

    @Getter
    @Setter
    @Column(name = "ante_post", nullable = true)
    private int antePost = 0;

    @Getter
    @Setter
    @Column(name = "permite_baixa_manual", nullable = true)
    private Boolean permiteBaixaManual;

    @Getter
    @Setter
    @Column(name = "controle_prazo", nullable = true)
    private int controleDePrazo = 0;

    @Getter
    @Setter
    @Column(name = "is_ativo")
    private Boolean ativo = false;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "ot_servicos_classificacoes", joinColumns = {
        @JoinColumn(name = "fk_servicos_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
        @JoinColumn(name = "fk_classificacoes_id", referencedColumnName = "id")
    })
    private Set<Classificacao> classificacoes;

    /* ************************************************************************
     * Serviço Gerado para meses do ano.
     * ********************************************************************* */
    @Getter
    @Setter
    @Column(name = "is_jan")
    private Boolean mesJan = false;

    @Getter
    @Setter
    @Column(name = "is_fev")
    private Boolean mesFev = false;

    @Getter
    @Setter
    @Column(name = "is_mar")
    private Boolean mesMar = false;

    @Getter
    @Setter
    @Column(name = "is_abr")
    private Boolean mesAbr = false;

    @Getter
    @Setter
    @Column(name = "is_mai")
    private Boolean mesMai = false;

    @Getter
    @Setter
    @Column(name = "is_jun")
    private Boolean mesJun = false;

    @Getter
    @Setter
    @Column(name = "is_jul")
    private Boolean mesJul = false;

    @Getter
    @Setter
    @Column(name = "is_ago")
    private Boolean mesAgo = false;

    @Getter
    @Setter
    @Column(name = "is_set")
    private Boolean mesSet = false;

    @Getter
    @Setter
    @Column(name = "is_out")
    private Boolean mesOut = false;

    @Getter
    @Setter
    @Column(name = "is_nov")
    private Boolean mesNov = false;

    @Getter
    @Setter
    @Column(name = "is_dez")
    private Boolean mesDez = false;

    /* ************************************************************************
     * Serviço Gerado para situações da Empresa.
     * ********************************************************************** */
    @Getter
    @Setter
    @Column(name = "is_ativa")
    private Boolean isAtiva = false;

    @Getter
    @Setter
    @Column(name = "is_suspenso")
    private Boolean isSuspenso = false;

    @Getter
    @Setter
    @Column(name = "is_inativo")
    private Boolean isInativo = false;

    @Getter
    @Setter
    @Column(name = "is_paralisado")
    private Boolean isParalisado = false;

    @Getter
    @Setter
    @Column(name = "is_baixada")
    private Boolean isBaixada = false;

    @Getter
    @Setter
    @Column(name = "is_rescindida")
    private Boolean isRescindida = false;

    @Getter
    @Setter
    @Column(name = "is_processo_baixa")
    private Boolean isProcessoBaixa = false;

    @Getter
    @Setter
    @Column(name = "is_processo_rescisao")
    private Boolean isProcessoRescisao = false;
    
//    @Getter @Setter
//    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "Servico")
//    private Set<Categoria> categorias = new HashSet<>();
    
}
