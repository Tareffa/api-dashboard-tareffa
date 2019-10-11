package br.com.ottimizza.dashboard.repositories.grafico;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GraficoRepository extends JpaRepository<Grafico, BigInteger>, GraficoRepositoryCustom{
    
    @Modifying
    @Transactional
    @Query( value =
            "   DELETE FROM ot_grafico grafico                                      \n" +
            "       WHERE grafico.id IN (                                           \n" +
            "       	SELECT grafico.id FROM ot_grafico                           \n" +
            "               INNER JOIN ot_indicador indicador                       \n" +
            "                   ON ot_grafico.fk_indicador_id = indicador.id        \n" +
            "               WHERE indicador.fk_contabilidade_id = :contabilidadeId  \n" +
            "               AND indicador.id = :indicadorId                         \n" +
            "       )                                                               \n" ,
            nativeQuery = true)
    public void deleteGraficoByIndicadorId(@Param("indicadorId") BigInteger indicadorId, @Param("contabilidadeId") Long usuarioContabilidadeId);
    
}
