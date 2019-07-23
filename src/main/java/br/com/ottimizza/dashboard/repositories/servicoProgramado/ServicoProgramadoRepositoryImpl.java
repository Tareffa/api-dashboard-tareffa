package br.com.ottimizza.dashboard.repositories.servicoProgramado;

import br.com.ottimizza.dashboard.models.servicos.QServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramado;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
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

    @Override
    public Long contadorServicoProgramado(ServicoProgramadoFiltroAvancado filtro) {
        
        JPAQuery query = new JPAQuery(em);
        query.from(servicoProgramado);
        
        //STATUS SERVIÇO PROGRAMADO
        if(filtro.getStatus() != null) query.where(servicoProgramado.status.in(filtro.getStatus()));
        
        return query.fetchCount();
    }
    
}
