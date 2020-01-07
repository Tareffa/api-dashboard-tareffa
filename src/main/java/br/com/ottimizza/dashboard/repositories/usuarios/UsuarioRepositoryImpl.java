package br.com.ottimizza.dashboard.repositories.usuarios;

import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoStatus;
import br.com.ottimizza.dashboard.models.caracteristica.caracteristica_empresas.QCaracteristicaEmpresa;
import br.com.ottimizza.dashboard.models.contabilidade.ContabilidadeShort;
import br.com.ottimizza.dashboard.models.contabilidade.QContabilidadeShort;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.QGraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.QGraficoServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioImagens;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    private QUsuario usuario = QUsuario.usuario;
    private QContabilidadeShort contabilidade = QContabilidadeShort.contabilidadeShort;

    private QServicoProgramado servicoProgramado = QServicoProgramado.servicoProgramado;
    private QGraficoServico graficoServico = QGraficoServico.graficoServico;
    
    private QGraficoCaracteristica graficoCaracteristica = QGraficoCaracteristica.graficoCaracteristica;
    private QCaracteristicaEmpresa caracteristicaEmpresa = QCaracteristicaEmpresa.caracteristicaEmpresa;
    
    @Override
    public Usuario findByEmail(String email) {
        JPAQuery<Usuario> query = new JPAQuery<Usuario>(em).from(usuario).where(usuario.email.eq(email));
        return query.fetchFirst();
    }

    @Override
    public String findLogoAccountingFromUser(Long contabilidadeId) {
        try {
            JPAQuery<ContabilidadeShort> query = new JPAQuery<ContabilidadeShort>(em).from(contabilidade).where(contabilidade.id.eq(contabilidadeId));
            ContabilidadeShort resultado = query.fetchFirst();
            return resultado.getUrlLogotipo();
        } catch (Exception e) {
            return "Error";
        }
    }

    @Override
    public Long contadorServicoProgramadoPorUsuarioGraficoId(Long usuarioId, BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado) {
        try {
        if(autenticado == null) return 0L;
        
        JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(usuario)
                    .on(servicoProgramado.alocadoPara.id.eq(usuario.id).and(usuario.id.eq(usuarioId)))
                .innerJoin(graficoServico).on(
                    servicoProgramado.servico.id.eq(graficoServico.servico.id)
                    .and(graficoServico.grafico.id.eq(graficoId))
                    ) //JOIN GRAFICO/SERVICO
                .innerJoin(caracteristicaEmpresa).on(servicoProgramado.cliente.id.eq(caracteristicaEmpresa.empresa.id)) //JOIN CARACTERISTICA/EMPRESA
                .innerJoin(graficoCaracteristica).on(
                    caracteristicaEmpresa.caracteristica.id.eq(graficoCaracteristica.caracteristica.id)
                    .and(graficoCaracteristica.grafico.id.eq(graficoId))
                ); //JOIN GRÁFICO/CARACTERÍSTICA
            
            //DATA PROGRAMADA
            if(filtro.getDataProgramadaInicio() != null && filtro.getDataProgramadaTermino() != null){
                query.where(servicoProgramado.dataProgramadaEntrega.goe(filtro.getDataProgramadaInicio())
                    .and(servicoProgramado.dataProgramadaEntrega.loe(filtro.getDataProgramadaTermino())));
            }
            
            /*** FILTRO SERVIÇOS PROGRAMADOS ***/
                //CONTABILIDADE
                //query.where(servico.contabilidade.id.eq(autenticado.getContabilidade().getId()));
                query.where(servicoProgramado.ativo.ne(false));

                //--STATUS
                if(filtro.getSituacao() != null){

                    //---ABERTO
                    if(filtro.getSituacao() == ServicoProgramadoSituacao.ABERTO) {
                        query.where(servicoProgramado.status.in(ServicoProgramadoStatus.NAO_INICIADO,ServicoProgramadoStatus.INICIADO));
                        
                        if(filtro.getPrazo() != null){
                            Date dataAtual = new Date();
                            BooleanBuilder prazos = new BooleanBuilder();
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.NO_PRAZO))
                                prazos.or(servicoProgramado.dataProgramadaEntrega.goe(dataAtual));
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.ATRASADO))
                                prazos.or(servicoProgramado.dataProgramadaEntrega.lt(dataAtual).and(servicoProgramado.dataVencimento.goe(dataAtual)));
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.VENCIDO))
                                prazos.or(servicoProgramado.dataVencimento.lt(dataAtual));
                            
                            query.where(prazos);
                        }
                    }

                    //---ENCERRADO
                    if(filtro.getSituacao() == ServicoProgramadoSituacao.ENCERRADO){
                        query.where(servicoProgramado.status.in(ServicoProgramadoStatus.CONCLUIDO,ServicoProgramadoStatus.ENVIADO));

                        if(filtro.getPrazo() != null){
                            BooleanBuilder prazos = new BooleanBuilder();
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.NO_PRAZO))
                                prazos.or(servicoProgramado.dataProgramadaEntrega.goe(servicoProgramado.dataTermino));
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.ATRASADO))
                                prazos.or(servicoProgramado.dataProgramadaEntrega.lt(servicoProgramado.dataTermino).and(servicoProgramado.dataVencimento.goe(servicoProgramado.dataTermino)));
                            
                            if(filtro.getPrazo().contains(ServicoProgramadoPrazo.VENCIDO))
                                prazos.or(servicoProgramado.dataVencimento.lt(servicoProgramado.dataTermino));
                            
                            query.where(prazos);
                        } 
                    }
                }
            /*** FIM FILTRO SERVIÇOS PROGRAMADOS ***/

            return query.fetchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Long(-1);
        }
    }

    @Override
    public UsuarioImagens findImagesFromUser(Long usuarioId) {
        try {
            JPAQuery<UsuarioImagens> query = new JPAQuery<UsuarioImagens>(em).from(usuario)
                .innerJoin(contabilidade).on(usuario.contabilidade.id.eq(contabilidade.id).and(usuario.id.eq(usuarioId)));
            
            query.select(Projections.constructor(UsuarioImagens.class, usuario.urlFoto, contabilidade.urlLogotipo));
            
            return query.fetchFirst();
        } catch (Exception e) {
            return null;
        }
    }
}