package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoStatus;
import br.com.ottimizza.dashboard.models.departamentos.QDepartamento;
import br.com.ottimizza.dashboard.models.empresas.QEmpresaShort;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jdk.nashorn.internal.objects.NativeDate;
import org.springframework.stereotype.Repository;

@Repository
public class ServicoProgramadoRepositoryImpl implements ServicoProgramadoRepositoryCustom {
    
    @PersistenceContext
    EntityManager em;
    
    private QServicoProgramado servicoProgramado = QServicoProgramado.servicoProgramado;
    private QUsuario usuario = QUsuario.usuario;
    private QDepartamento departamento = QDepartamento.departamento;
    private QEmpresaShort empresa = QEmpresaShort.empresaShort;
    private QServico servico = QServico.servico;

    @Override
    public Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro) {
        
        JPAQuery query = new JPAQuery(em);
        query.from(servicoProgramado)
            .innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id)) //JOIN SERVIÇO
            .innerJoin(empresa).on(servicoProgramado.cliente.id.eq(empresa.id)) //JOIN EMPRESA
            .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id)) //JOIN USUÁRIO
            .innerJoin(departamento).on(usuario.departamento.id.eq(departamento.id)); //JOIN DEPARTAMENTO
        
        /*** FILTRO SERVIÇOS PROGRAMADOS ***/
        
            //STATUS
            if(filtro.getSituacao() != null){
                
                //ABERTO
                if(filtro.getSituacao() == ServicoProgramadoSituacao.ABERTO) {
                    query.where(servicoProgramado.status.in(ServicoProgramadoStatus.NAO_INICIADO,ServicoProgramadoStatus.INICIADO));
                
                    if(filtro.getPrazo() != null){
                        Date dataAtual = new Date();
                        if(filtro.getPrazo() == ServicoProgramadoPrazo.NO_PRAZO)
                            query.where(servicoProgramado.dataProgramadaEntrega.goe(dataAtual));
                        
                        if(filtro.getPrazo() == ServicoProgramadoPrazo.ATRASADO){
                            query.where(servicoProgramado.dataProgramadaEntrega.lt(dataAtual));
                            query.where(servicoProgramado.dataVencimento.goe(dataAtual));
                        }
                        
                        if(filtro.getPrazo() == ServicoProgramadoPrazo.VENCIDO)
                            query.where(servicoProgramado.dataVencimento.lt(dataAtual));
                    }
                }
                
                //ENCERRADO
                if(filtro.getSituacao() == ServicoProgramadoSituacao.ENCERRADO){
                    query.where(servicoProgramado.status.in(ServicoProgramadoStatus.CONCLUIDO,ServicoProgramadoStatus.ENVIADO));
                 
                    if(filtro.getPrazo() != null){
                        if(filtro.getPrazo() == ServicoProgramadoPrazo.NO_PRAZO)
                            query.where(servicoProgramado.dataProgramadaEntrega.goe(servicoProgramado.dataTermino));
                        
                        if(filtro.getPrazo() == ServicoProgramadoPrazo.ATRASADO){
                            query.where(servicoProgramado.dataProgramadaEntrega.lt(servicoProgramado.dataTermino));
                            query.where(servicoProgramado.dataVencimento.goe(servicoProgramado.dataTermino));
                        }
                        
                        if(filtro.getPrazo() == ServicoProgramadoPrazo.VENCIDO)
                            query.where(servicoProgramado.dataVencimento.lt(servicoProgramado.dataTermino));
                    }
                    
                }
                
            }
            
            //DEPARTAMENTO
            if(filtro.getDepartamento() != null && filtro.getDepartamento().getId() != null) 
            query.where(departamento.id.in(filtro.getDepartamento().getId()));

            //USUARIO
            if(filtro.getUsuario() != null && filtro.getUsuario().getId() != null) 
            query.where(usuario.id.in(filtro.getUsuario().getId()));

            //SERVIÇO
            if(filtro.getServico() != null && filtro.getServico().getId() != null) 
            query.where(servico.id.in(filtro.getServico().getId()));

            //EMPRESA
            if(filtro.getEmpresa() != null && filtro.getEmpresa().getId() != null) 
            query.where(empresa.id.in(filtro.getEmpresa().getId()));

        /*** FIM FILTRO SERVIÇOS PROGRAMADOS ***/
        
        return query.fetchCount();
    }
    
}
