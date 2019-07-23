package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.departamentos.QDepartamento;
import br.com.ottimizza.dashboard.models.empresas.QEmpresa;
import br.com.ottimizza.dashboard.models.empresas.QEmpresaShort;
import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.QUsuario;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
            .innerJoin(servico).on(servicoProgramado.servico.id.eq(servico.id))
            .innerJoin(empresa).on(servicoProgramado.cliente.id.eq(empresa.id))
            .innerJoin(usuario).on(servicoProgramado.alocadoPara.id.eq(usuario.id))
            .innerJoin(departamento).on(usuario.departamento.id.eq(departamento.id));
        
        //*** FILTRO SERVIÇOS PROGRAMADOS ***//
        //STATUS
        if(filtro.getStatus() != null)query.where(servicoProgramado.status.in(filtro.getStatus()));
        
        //DEPARTAMENTO
        if(filtro.getDepartamento() != null && filtro.getDepartamento().getId() != null) 
        query.where(departamento.id.in(filtro.getDepartamento().getId()));
        
        //USUARIO
//        if(filtro.getUsuario() != null && filtro.getUsuario().getId() != null) 
//        query.where(servicoProgramado.status.in(filtro.getStatus()));
        
        return query.fetchCount();
    }
    
}
