package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.services.ServicoProgramadoService;
import br.com.ottimizza.dashboard.services.UserService;
import java.security.Principal;
import javax.inject.Inject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/servico/programado")
public class ServicoProgramadoController {

    @Inject
    ServicoProgramadoService servicoProgramadoService;
 
    @Inject
    UserService userService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    // <editor-fold defaultstate="collapsed" desc="Find company by ID">
    public ResponseEntity<String> countServiceProgram(Principal principal, @RequestBody ServicoProgramadoFiltroAvancado filtro)
            throws Exception {

        // Get Authorized User by Username.
        System.out.println("Principal: " + principal.getName());
        
        //User authorized = userService.findByUsername(principal.getName());
        //ServicoProgramadoFiltroAvancado filtro = new ServicoProgramadoFiltroAvancado();

        return ResponseEntity.ok(servicoProgramadoService.count(filtro).toString());
    }

    @PostMapping(path = "agrupamento/{tipo}", produces = MediaType.APPLICATION_JSON_VALUE)
    // <editor-fold defaultstate="collapsed" desc="Find company by ID">
    public ResponseEntity<String> findGroupBy(Principal principal, @PathVariable("tipo") Short agrupamento, @RequestBody ServicoProgramadoFiltroAvancado filtro)
            throws Exception {
        return ResponseEntity.ok(servicoProgramadoService.countGroupBy(agrupamento, filtro).toString());
    }
    
}
