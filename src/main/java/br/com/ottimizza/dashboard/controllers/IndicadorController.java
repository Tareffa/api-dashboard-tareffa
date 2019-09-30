package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;
import br.com.ottimizza.dashboard.services.IndicadorService;
import java.security.Principal;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/indicador")
public class IndicadorController {
    
    @Context
    HttpServletRequest context;
    
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    IndicadorService indicadorService;
    
    @PostMapping
    //<editor-fold defaultstate="collapsed" desc="Busca caracteristicas">
    public ResponseEntity<String> getCaracteristicas(@RequestBody Indicador indicador, Principal principal) throws Exception{
        // Get User by Email.
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(indicadorService.save(indicador, autenticado).toString());
    }
    //</editor-fold>
    
}
