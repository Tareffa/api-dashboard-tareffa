package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;
import br.com.ottimizza.dashboard.services.CaracteristicaService;
import java.security.Principal;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caracteristica")
public class CaracteristicaController {
    
    @Context
    HttpServletRequest context;
    
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    CaracteristicaService caracteristicaService;
    
    @GetMapping
    //<editor-fold defaultstate="collapsed" desc="Busca caracteristicas">
    public ResponseEntity<String> getCaracteristicas(@RequestParam("description") String descricao, Principal principal) throws Exception{
        // Get User by Email.
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(caracteristicaService.getCaracteristicas(descricao, autenticado).toString());
    }
    //</editor-fold>
    
}
