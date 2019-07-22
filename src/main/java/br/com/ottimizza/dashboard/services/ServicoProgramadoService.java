package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.repositories.servicoProgramado.ServicoProgramadoRepository;

import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class ServicoProgramadoService {

    @Inject
    private ServicoProgramadoRepository repository;

    //<editor-fold defaultstate="collapsed" desc="Save">
    public Long count(ServicoProgramadoFiltroAvancado filtro)throws Exception{
        return repository.contadorServicoProgramado(filtro);
    }
    //</editor-fold>

}
