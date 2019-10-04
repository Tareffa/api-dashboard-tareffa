package br.com.ottimizza.dashboard.repositories.servico;

import br.com.ottimizza.dashboard.models.servicos.QServico;
import br.com.ottimizza.dashboard.models.servicos.Servico;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import com.querydsl.jpa.impl.JPAQuery;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ServicoRepositoryImpl implements ServicoRepositoryCustom{
    
    @PersistenceContext
    EntityManager em;
    
    private QServico servico = QServico.servico;

    @Override
    public Servico buscarServicoPorId(Long servicoId, Usuario usuario) {
        try {
            JPAQuery<Servico> query = new JPAQuery(em);
            query.from(servico)
                .where(servico.contabilidade.id.eq(usuario.getContabilidade().getId()))
                .where(servico.id.eq(servicoId));

            return query.fetchOne();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Boolean verificarExistenciaServicoPorId(Long servicoId, Usuario usuario) {
        try {
            JPAQuery<Servico> query = new JPAQuery(em);
            query.from(servico)
                .where(servico.contabilidade.id.eq(usuario.getContabilidade().getId()))
                .where(servico.id.eq(servicoId));

            return query.fetchCount() > 0;
        } catch (Exception e) {
            return null;
        }
    }

}
