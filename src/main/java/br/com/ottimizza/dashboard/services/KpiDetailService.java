package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.KpiDetail;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import br.com.ottimizza.dashboard.repositories.kpi_detail.KpiDetailRepository;

import java.math.BigInteger;
import java.util.List;

@Service
public class KpiDetailService {
    
    @Inject
    private KpiDetailRepository repository;
    
    //<editor-fold defaultstate="collapsed" desc="Save">
    public KpiDetail save(KpiDetail kpi) throws Exception{
        return repository.save(kpi);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Find by Id">
    public Optional<KpiDetail> findById(Long idKpi) throws Exception{
        return repository.findById(idKpi);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Find by List CNPJ">
    public List<KpiDetail> findByListCNPJ(List<String> cnpj)throws Exception{
        return repository.findKpiDetailsByCNPJ(cnpj);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Update by Id">
    public JSONObject updateById(BigInteger idKpi, KpiDetail kpi) throws Exception{
        JSONObject response = new JSONObject();
        try {
//        	Optional<KpiDetail> kpiOptional = repository.findById(idKpi);
//        	KpiDetail kpiOptional = repository.findById(idKpi);
//            
//            if(!kpiOptional.isPresent()) throw new NoResultException();
//                
//            kpi.setId(idKpi);
//            repository.save(kpi);
//            
//            response.put("status","sucess");
//            response.put("message","Atualizado detalhe do kpi com sucesso!");
            
        } catch (NoResultException e0) {
            response.put("status","error");
            response.put("message","Problema ao encontrar o detalhe do kpi!");
            throw new NoResultException(response.toString()) ;
        } catch (Exception e1) {
            response.put("status","Error");
            response.put("message","Houve um problema ao atualizar!");
            throw new Exception(response.toString()) ;
        }
        return response;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Delete by Id">
    public JSONObject delete(Long idKpi)throws Exception{
        JSONObject response = new JSONObject();
        try {
            repository.deleteById(idKpi);
            response.put("status","sucess");
            response.put("message","Excluído kpi com sucesso!");
        } catch (Exception e) {
            response.put("status","Error");
            response.put("message","Houve um problema ao excluir!");
            return response;
        }
        return response;
    }
    //</editor-fold>
    
}
