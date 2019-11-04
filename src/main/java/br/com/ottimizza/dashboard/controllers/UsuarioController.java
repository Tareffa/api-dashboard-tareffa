package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;
import br.com.ottimizza.dashboard.services.UsuarioService;
import java.security.Principal;
import javax.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/usuario" })
public class UsuarioController {

    @Inject
    UsuarioService usuarioService;

    @Inject
    UsuarioRepository usuarioRepository;
    
    @GetMapping(path = "imagens")
    public ResponseEntity<String> findImagesFromUser(Principal principal) throws Exception {
        
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(usuarioService.findImagesFromUser(autenticado.getId()).toString());
    }
    
    @GetMapping(path = "logo/contabilidade")
    public ResponseEntity<String> findLogoAccountingFromUser(Principal principal) throws Exception {
        
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        
        return ResponseEntity.ok(usuarioService.findLogoAccountingFromUser(autenticado.getContabilidade().getId()).toString());
    }
}