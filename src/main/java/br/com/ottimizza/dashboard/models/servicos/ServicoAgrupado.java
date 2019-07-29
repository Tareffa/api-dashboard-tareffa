package br.com.ottimizza.dashboard.models.servicos;

public class ServicoAgrupado {
    
    private String nomeServico;
    private Long servicosProgramadosContagem;

    public ServicoAgrupado(String nomeServico, Long servicosProgramadosContagem) {
        this.nomeServico = nomeServico;
        this.servicosProgramadosContagem = servicosProgramadosContagem;
    }

    public ServicoAgrupado() {
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public Long getServicosProgramadosContagem() {
        return servicosProgramadosContagem;
    }

    public void setServicosProgramadosContagem(Long servicosProgramadosContagem) {
        this.servicosProgramadosContagem = servicosProgramadosContagem;
    } 
    
}
