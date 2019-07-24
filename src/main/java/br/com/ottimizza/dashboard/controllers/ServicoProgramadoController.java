package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.services.ServicoProgramadoService;
import br.com.ottimizza.dashboard.services.UserService;
import java.security.Principal;
import javax.inject.Inject;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("contar")
    // <editor-fold defaultstate="collapsed" desc="Find company by ID">
    public ResponseEntity<JSONObject> findCompanyByID(Principal principal, @RequestBody ServicoProgramadoFiltroAvancado filtro)
            throws Exception {

        // Get Authorized User by Username.
        //User authorized = userService.findByUsername(principal.getName());
        //ServicoProgramadoFiltroAvancado filtro = new ServicoProgramadoFiltroAvancado();

        return ResponseEntity.ok(servicoProgramadoService.count(filtro));
    }

}
