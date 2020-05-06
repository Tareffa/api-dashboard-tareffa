package br.com.ottimizza.dashboard.repositories.grafico;

import br.com.ottimizza.dashboard.constraints.ServicoProgramadoPrazo;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoSituacao;
import br.com.ottimizza.dashboard.constraints.ServicoProgramadoStatus;
import br.com.ottimizza.dashboard.models.caracteristica.caracteristica_empresas.QCaracteristicaEmpresa;
import br.com.ottimizza.dashboard.models.departamentos.DepartamentoShort;
import br.com.ottimizza.dashboard.models.departamentos.QDepartamento;
import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.GraficoDashboard;
import br.com.ottimizza.dashboard.models.graficos.GraficoShort;
import br.com.ottimizza.dashboard.models.graficos.QGrafico;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.QGraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.QGraficoServico;
import br.com.ottimizza.dashboard.models.indicadores.QIndicador;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.models.usuarios.UsuarioDashboard;
import br.com.ottimizza.dashboard.models.usuarios.usuarios_unidade_negocio.QUsuarioUnidadeNegocio;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class GraficoRepositoryImpl implements GraficoRepositoryCustom{
    
    @PersistenceContext
    EntityManager em;
    
    private QUsuario usuario = QUsuario.usuario;
    private QDepartamento departamento = QDepartamento.departamento;
    private QServico servico = QServico.servico;
    
    private QGrafico grafico = QGrafico.grafico;
    private QIndicador indicador = QIndicador.indicador;
    
    private QServicoProgramado servicoProgramado = QServicoProgramado.servicoProgramado;
    private QGraficoServico graficoServico = QGraficoServico.graficoServico;
    
    private QGraficoCaracteristica graficoCaracteristica = QGraficoCaracteristica.graficoCaracteristica;
    private QCaracteristicaEmpresa caracteristicaEmpresa = QCaracteristicaEmpresa.caracteristicaEmpresa;

    private QUsuarioUnidadeNegocio usuarioUnidadeNegocio = QUsuarioUnidadeNegocio.usuarioUnidadeNegocio;

    @Override
    public Grafico buscarGraficoPorId(BigInteger graficoId, Usuario autenticado) {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico)
                .innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(autenticado.getContabilidade().getId())) //CONTABILIDADE
                .where(grafico.id.eq(graficoId)); //INDICADOR ID

            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<?> buscarListaDeGraficos(Usuario autenticado) {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico).innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(autenticado.getContabilidade().getId())); //CONTABILIDADE
            query.select(Projections.constructor(GraficoShort.class, grafico.id, grafico.nomeGrafico));
            
            return query.orderBy(grafico.nomeGrafico.asc()).fetch();
        } catch (Exception e) {
            return null;
        }
    }    

    @Override
    public Boolean verificarExistenciaGraficoPorId(BigInteger graficoId, Usuario autenticado) {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico)
                .innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(autenticado.getContabilidade().getId())) //CONTABILIDADE
                .where(grafico.id.eq(graficoId)); //INDICADOR ID

            return query.fetchCount() > 0;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GraficoShort> buscarListaDeGraficosPorIndicadorId(BigInteger indicadorId, Usuario usuario) {
        try {
            JPAQuery<GraficoShort> query = new JPAQuery(em);
            query.from(grafico).innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(usuario.getContabilidade().getId())) //CONTABILIDADE
                .where(indicador.id.eq(indicadorId));
            query.select(Projections.constructor(GraficoShort.class, grafico.id, grafico.nomeGrafico));

            return query.orderBy(grafico.nomeGrafico.asc()).fetch();
        } catch (Exception e) {
                return null;
        }
    }
    
    @Override
    public List<GraficoDashboard> buscarListaDeGraficosDashboardPorIndicadorId(BigInteger indicadorId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado) {
        List<Long> departamentosId = new ArrayList<>();
        try {
            if(autenticado == null) return null;

            JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(graficoServico)                                                                  //JOIN GRAFICO/SERVICO
                    .on(servicoProgramado.servico.id.eq(graficoServico.servico.id))
                .innerJoin(caracteristicaEmpresa)                                                           //JOIN CARACTERISTICA/EMPRESA
                    .on(servicoProgramado.cliente.id.eq(caracteristicaEmpresa.empresa.id))
                .innerJoin(graficoCaracteristica)                                                           //JOIN GRÁFICO/CARACTERÍSTICA
                    .on(caracteristicaEmpresa.caracteristica.id.eq(graficoCaracteristica.caracteristica.id))
                .innerJoin(grafico)                                                                         //JOIN GRAFICO
                    .on(
                        graficoServico.grafico.id.eq(grafico.id)
                        .and(graficoCaracteristica.grafico.id.eq(grafico.id))
                        .and(grafico.indicador.id.eq(indicadorId))
                    );
            
            //RESTRINGIR UNIDADE DE NEGÓCIO
            boolean restringirUnidadeNegocio = (autenticado.getRestringirUnidadeNegocio() != null && autenticado.getRestringirUnidadeNegocio());
            if(restringirUnidadeNegocio){
                query.innerJoin(usuarioUnidadeNegocio)
                    .on(caracteristicaEmpresa.caracteristica.id.eq(usuarioUnidadeNegocio.id.unidadeNegocioId)
                        .and(usuarioUnidadeNegocio.id.usuarioId.eq(autenticado.getId())));
            }
            
            query.where(servicoProgramado.ativo.isTrue().or(servicoProgramado.ativo.isNull()));

            /*** FILTRO SERVIÇOS PROGRAMADOS ***/
            //DATA PROGRAMADA
            if(filtro.getDataProgramadaInicio() != null && filtro.getDataProgramadaTermino() != null){
                query.where(servicoProgramado.dataProgramadaEntrega.goe(filtro.getDataProgramadaInicio())
                    .and(servicoProgramado.dataProgramadaEntrega.loe(filtro.getDataProgramadaTermino())));
            }

            //--DEPARTAMENTO
            if(filtro.getDepartamento() != null){

                query.innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id)) //JOIN SERVIÇO
                .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id)) //JOIN USUÁRIO
                .innerJoin(departamento).on(departamento.id.eq( //JOIN DEPARTAMENTO
                    new CaseBuilder.Initial(usuario.departamento.id.isNull()).then(servico.grupoServico.id)
                        .otherwise(usuario.departamento.id))
                );

                if(!filtro.getDepartamento().isEmpty()){
                    for (DepartamentoShort departamentoShort : filtro.getDepartamento()) {
                        departamentosId.add(departamentoShort.getId());
                    }

                    query.where(departamento.id.in(departamentosId));
                }
            }   
            /*** FIM FILTRO SERVIÇOS PROGRAMADOS ***/


            /*** SELECT DOS TIPOS DE SERVIÇO PROGRAMADO PARA CONTAGEM ***/
            //DATA ATUAL
            Date dataAtual = new Date();

            //ABERTO NO PRAZO
            NumberExpression<Long> abertoNoPrazo = new CaseBuilder().when(
                servicoProgramado.status.in(ServicoProgramadoStatus.NAO_INICIADO,ServicoProgramadoStatus.INICIADO)
                    .and(servicoProgramado.dataProgramadaEntrega.goe(dataAtual))
            ).then(new Long(1)).otherwise(new Long(0));

            //ABERTO ATRASADO
            BooleanExpression atrasadoAberto = servicoProgramado.dataProgramadaEntrega.lt(dataAtual).and(servicoProgramado.dataVencimento.goe(dataAtual));
            NumberExpression<Long> abertoAtrasado = new CaseBuilder().when(
                servicoProgramado.status.in(ServicoProgramadoStatus.NAO_INICIADO,ServicoProgramadoStatus.INICIADO)
                    .and(atrasadoAberto.or(servicoProgramado.dataVencimento.lt(dataAtual))
                    )
            ).then(new Long(1)).otherwise(new Long(0));

            //ENCERRADO NO PRAZO
            NumberExpression<Long> encerradoNoPrazo = new CaseBuilder().when(
                servicoProgramado.status.in(ServicoProgramadoStatus.CONCLUIDO,ServicoProgramadoStatus.ENVIADO)
                    .and(servicoProgramado.dataProgramadaEntrega.goe(servicoProgramado.dataTermino))
            ).then(new Long(1)).otherwise(new Long(0));
            
            //ENCERRADO ATRASADO
            BooleanExpression atrasadoEncerrado = servicoProgramado.dataProgramadaEntrega.lt(servicoProgramado.dataTermino).and(servicoProgramado.dataVencimento.goe(servicoProgramado.dataTermino));
            NumberExpression<Long> encerradoAtrasado = new CaseBuilder().when(
                servicoProgramado.status.in(ServicoProgramadoStatus.CONCLUIDO,ServicoProgramadoStatus.ENVIADO)
                    .and(atrasadoEncerrado.or(servicoProgramado.dataVencimento.lt(servicoProgramado.dataTermino))
                )
            ).then(new Long(1)).otherwise(new Long(0));
            /*** FIM SELECT DOS TIPOS DE SERVIÇO PROGRAMADO PARA CONTAGEM ***/

            query.groupBy(grafico.nomeGrafico, grafico.id); //AGRUPAMENTO
            query.orderBy(grafico.nomeGrafico.asc());       //ORDENAÇÃO

            query.select(   //SELECT
                Projections.constructor(GraficoDashboard.class, 
                    grafico.id, 
                    grafico.nomeGrafico,
                    abertoNoPrazo.sum(),
                    abertoAtrasado.sum(),
                    encerradoNoPrazo.sum(),
                    encerradoAtrasado.sum()
                )
            );

            return query.fetch();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean verificaExistenciaNomeDeGraficos(String nomeGrafico, BigInteger indicadorId, Usuario autenticado) throws Exception {
        try {
            JPAQuery<Grafico> query = new JPAQuery(em);
            query.from(grafico).innerJoin(indicador).on(grafico.indicador.id.eq(indicador.id))
                .where(indicador.contabilidade.id.eq(autenticado.getContabilidade().getId())) //CONTABILIDADE
                .where(indicador.id.eq(indicadorId)) //CONTABILIDADE
                .where(grafico.nomeGrafico.eq(nomeGrafico));
            
            return query.fetchCount() > 0;
        } catch (Exception e) {
            throw new Exception("Error");
        }
    }

    @Override
    public Long contadorServicoProgramadoPorGraficoId(BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado) {
        try {
        if(autenticado == null) return 0L;
        
        JPAQuery query = new JPAQuery(em);
            query.from(servicoProgramado)
                .innerJoin(graficoServico).on(
                    servicoProgramado.servico.id.eq(graficoServico.servico.id)
                    .and(graficoServico.grafico.id.eq(graficoId))
                    ) //JOIN GRAFICO/SERVICO
                .innerJoin(caracteristicaEmpresa).on(servicoProgramado.cliente.id.eq(caracteristicaEmpresa.empresa.id)) //JOIN CARACTERISTICA/EMPRESA
                .innerJoin(graficoCaracteristica).on(
                    caracteristicaEmpresa.caracteristica.id.eq(graficoCaracteristica.caracteristica.id)
                    .and(graficoCaracteristica.grafico.id.eq(graficoId))
                ); //JOIN GRÁFICO/CARACTERÍSTICA

            //RESTRINGIR UNIDADE DE NEGÓCIO
            boolean restringirUnidadeNegocio = (autenticado.getRestringirUnidadeNegocio() != null && autenticado.getRestringirUnidadeNegocio());
            if(restringirUnidadeNegocio){
                query.innerJoin(usuarioUnidadeNegocio)
                    .on(caracteristicaEmpresa.caracteristica.id.eq(usuarioUnidadeNegocio.id.unidadeNegocioId)
                        .and(usuarioUnidadeNegocio.id.usuarioId.eq(autenticado.getId())));
            }
            
            /*** FILTRO SERVIÇOS PROGRAMADOS ***/
                //CONTABILIDADE
                //query.where(servico.contabilidade.id.eq(autenticado.getContabilidade().getId()));
                query.where(servicoProgramado.ativo.isTrue().or(servicoProgramado.ativo.isNull()));

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
                
                //DATA PROGRAMADA
                if(filtro.getDataProgramadaInicio() != null && filtro.getDataProgramadaTermino() != null){
                    query.where(servicoProgramado.dataProgramadaEntrega.goe(filtro.getDataProgramadaInicio())
                        .and(servicoProgramado.dataProgramadaEntrega.loe(filtro.getDataProgramadaTermino())));
                }
            /*** FIM FILTRO SERVIÇOS PROGRAMADOS ***/

            return query.fetchCount();
        } catch (Exception e) {
            e.printStackTrace();
            return new Long(-1);
        }
    }

    @Override
    public List<UsuarioDashboard> buscarListaDeUsuariosPorGraficoId(BigInteger graficoId, ServicoProgramadoFiltroAvancado filtro, Usuario autenticado) {
        List<Long> departamentosId = new ArrayList<>();
        try {
            if(autenticado == null) return null;

            JPAQuery query = new JPAQuery(em);
                query.from(servicoProgramado)
                    .innerJoin(graficoServico).on(                                                                          //JOIN GRAFICO/SERVICO
                        servicoProgramado.servico.id.eq(graficoServico.servico.id)
                        .and(graficoServico.grafico.id.eq(graficoId)))
                    .innerJoin(caracteristicaEmpresa).on(servicoProgramado.cliente.id.eq(caracteristicaEmpresa.empresa.id)) //JOIN CARACTERISTICA/EMPRESA
                    .innerJoin(graficoCaracteristica).on(                                                                   //JOIN GRÁFICO/CARACTERÍSTICA
                        caracteristicaEmpresa.caracteristica.id.eq(graficoCaracteristica.caracteristica.id)
                        .and(graficoCaracteristica.grafico.id.eq(graficoId)))
                    .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id));                                //JOIN USUÁRIO (RESPONSÁVEL)

            //RESTRINGIR UNIDADE DE NEGÓCIO
            boolean restringirUnidadeNegocio = (autenticado.getRestringirUnidadeNegocio() != null && autenticado.getRestringirUnidadeNegocio());
            if(restringirUnidadeNegocio){
                query.innerJoin(usuarioUnidadeNegocio)
                    .on(caracteristicaEmpresa.caracteristica.id.eq(usuarioUnidadeNegocio.id.unidadeNegocioId)
                        .and(usuarioUnidadeNegocio.id.usuarioId.eq(autenticado.getId())));
            }

            query.where(servicoProgramado.ativo.isTrue().or(servicoProgramado.ativo.isNull()));

            /*** FILTRO SERVIÇOS PROGRAMADOS ***/
            //DATA PROGRAMADA
            if(filtro.getDataProgramadaInicio() != null && filtro.getDataProgramadaTermino() != null){
                query.where(servicoProgramado.dataProgramadaEntrega.goe(filtro.getDataProgramadaInicio())
                    .and(servicoProgramado.dataProgramadaEntrega.loe(filtro.getDataProgramadaTermino())));
            }

            //--DEPARTAMENTO
            if(filtro.getDepartamento() != null){
                
                query.innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id)) //JOIN SERVIÇO
                .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id)) //JOIN USUÁRIO
                .innerJoin(departamento).on(departamento.id.eq( //JOIN DEPARTAMENTO
                    new CaseBuilder.Initial(usuario.departamento.id.isNull()).then(servico.grupoServico.id)
                        .otherwise(usuario.departamento.id))
                );
                
                if(!filtro.getDepartamento().isEmpty()){
                    for (DepartamentoShort departamentoShort : filtro.getDepartamento()) {
                        departamentosId.add(departamentoShort.getId());
                    }

                    query.where(departamento.id.in(departamentosId));
                }
            }
            /*** FIM FILTRO SERVIÇOS PROGRAMADOS ***/

            /*** SELECT DOS TIPOS DE SERVIÇO PROGRAMADO PARA CONTAGEM ***/
            //DATA ATUAL
            Date dataAtual = new Date();
            
            //ABERTO NO PRAZO
            NumberExpression<Long> abertoNoPrazo = new CaseBuilder().when(
                servicoProgramado.status.in(ServicoProgramadoStatus.NAO_INICIADO,ServicoProgramadoStatus.INICIADO)
                    .and(servicoProgramado.dataProgramadaEntrega.goe(dataAtual))
            ).then(new Long(1)).otherwise(new Long(0));
            
            //ABERTO ATRASADO
            BooleanExpression atrasadoAberto = servicoProgramado.dataProgramadaEntrega.lt(dataAtual).and(servicoProgramado.dataVencimento.goe(dataAtual));
            NumberExpression<Long> abertoAtrasado = new CaseBuilder().when(
                servicoProgramado.status.in(ServicoProgramadoStatus.NAO_INICIADO,ServicoProgramadoStatus.INICIADO)
                    .and(atrasadoAberto.or(servicoProgramado.dataVencimento.lt(dataAtual))
                    )
            ).then(new Long(1)).otherwise(new Long(0));
            
            //ENCERRADO NO PRAZO
            NumberExpression<Long> encerradoNoPrazo = new CaseBuilder().when(
                servicoProgramado.status.in(ServicoProgramadoStatus.CONCLUIDO,ServicoProgramadoStatus.ENVIADO)
                    .and(servicoProgramado.dataProgramadaEntrega.goe(servicoProgramado.dataTermino))
            ).then(new Long(1)).otherwise(new Long(0));
            
            //ENCERRADO ATRASADO
            BooleanExpression atrasadoEncerrado = servicoProgramado.dataProgramadaEntrega.lt(servicoProgramado.dataTermino).and(servicoProgramado.dataVencimento.goe(servicoProgramado.dataTermino));
            NumberExpression<Long> encerradoAtrasado = new CaseBuilder().when(
                servicoProgramado.status.in(ServicoProgramadoStatus.CONCLUIDO,ServicoProgramadoStatus.ENVIADO)
                    .and(atrasadoEncerrado.or(servicoProgramado.dataVencimento.lt(servicoProgramado.dataTermino))
                )
            ).then(new Long(1)).otherwise(new Long(0));
            /*** FIM SELECT DOS TIPOS DE SERVIÇO PROGRAMADO PARA CONTAGEM ***/


            query.groupBy(usuario.nome, usuario.id); //AGRUPAMENTO
            query.orderBy(usuario.nome.asc());       //ORDENAÇÃO
            
            query.select(   //SELECT
                Projections.constructor(UsuarioDashboard.class, 
                    usuario.id, 
                    usuario.nome, 
                    usuario.urlFoto,
                    abertoNoPrazo.sum(),
                    abertoAtrasado.sum(),
                    encerradoNoPrazo.sum(),
                    encerradoAtrasado.sum()
                )
            );
            
            return query.fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}