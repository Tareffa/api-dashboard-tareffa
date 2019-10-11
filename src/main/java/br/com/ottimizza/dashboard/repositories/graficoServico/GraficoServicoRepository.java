package br.com.ottimizza.dashboard.repositories.graficoServico;

import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GraficoServicoRepository extends JpaRepository<GraficoServico, GraficoServicoID>, GraficoServicoRepositoryCustom{
    
    @Modifying
    @Transactional
    @Query( value =
            "   DELETE FROM ot_grafico_servico graficoServico                       \n" +
            "       WHERE graficoServico.fk_grafico_id IN (                         \n" +
            "       	SELECT grafico.id FROM ot_grafico grafico                   \n" +
            "               INNER JOIN ot_indicador indicador                       \n" +
            "                   ON grafico.fk_indicador_id = indicador.id           \n" +
            "               WHERE indicador.fk_contabilidade_id = :contabilidadeId  \n" +
            "               AND indicador.id = :indicadorId                         \n" +
            "       )                                                               \n" ,
            nativeQuery = true)
    public void deleteGraficoServicoByIndicadorId(@Param("indicadorId") BigInteger indicadorId, @Param("contabilidadeId") Long usuarioContabilidadeId);
    
}
