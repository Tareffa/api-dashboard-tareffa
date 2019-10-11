package br.com.ottimizza.dashboard.repositories.graficoCaracteristica;

import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristicaID;
import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GraficoCaracteristicaRepository extends JpaRepository<GraficoCaracteristica, GraficoCaracteristicaID>, GraficoCaracteristicaRepositoryCustom{
    
    @Modifying
    @Transactional
    @Query( value = 
            "   DELETE FROM ot_grafico_caracteristica graficoCaracteristica         \n" +
            "       WHERE graficoCaracteristica.fk_grafico_id IN (                  \n" +
            "       	SELECT grafico.id FROM ot_grafico grafico                   \n" +
            "               INNER JOIN ot_indicador indicador                       \n" +
            "                   ON grafico.fk_indicador_id = indicador.id           \n" +
            "               WHERE indicador.fk_contabilidade_id = :contabilidadeId  \n" +
            "               AND indicador.id = :indicadorId                         \n" +
            "       )                                                               \n" ,
            nativeQuery = true)
    public void deleteGraficoCaracteristicaByIndicadorId(@Param("indicadorId") BigInteger indicadorId, @Param("contabilidadeId") Long usuarioContabilidadeId);
    
    @Modifying
    @Transactional
    @Query( value =
            "   DELETE FROM ot_grafico_caracteristica graficoCaracteristica \n" +
            "       WHERE graficoCaracteristica.fk_grafico_id = :graficoId  \n",
            nativeQuery = true)
    public void deleteGraficoCaracteristicaByGraficoId(@Param("graficoId") BigInteger graficoId);
    
}
