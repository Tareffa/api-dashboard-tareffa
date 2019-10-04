package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;
import br.com.ottimizza.dashboard.services.GraficoService;
import java.math.BigInteger;
import java.security.Principal;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grafico")
public class GraficoController {

    @Context
    HttpServletRequest context;
    
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    GraficoService graficoService;
    
    @GetMapping("{id}")
    //<editor-fold defaultstate="collapsed" desc="Buscar grafico por id">
    public ResponseEntity<Grafico> buscaGraficoPorId(@PathVariable("id") BigInteger graficoId, Principal principal) throws Exception{
        // Get User by Email.
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(graficoService.getGraficoById(graficoId, autenticado));
    }
    //</editor-fold>
    
    @GetMapping
    //<editor-fold defaultstate="collapsed" desc="Buscar lista de graficos">
    public ResponseEntity<List> buscaGraficos(Principal principal) throws Exception{
        // Get User by Email.
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(graficoService.getListGraficos(autenticado));
    }
    //</editor-fold>
    
    @PostMapping
    //<editor-fold defaultstate="collapsed" desc="Criar grafico">
    public ResponseEntity<Grafico> saveGrafico(@RequestBody Grafico grafico, Principal principal) throws Exception{
        // Get User by Email.
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(graficoService.save(grafico, autenticado));
    }
    //</editor-fold>
    
    @DeleteMapping("{id}")
    //<editor-fold defaultstate="collapsed" desc="Excluir grafico">
    public ResponseEntity<String> deleteGrafico(@PathVariable("id") BigInteger graficoId, Principal principal) throws Exception{
        try {
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.delete(graficoId, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @PutMapping("{id}")
    //<editor-fold defaultstate="collapsed" desc="Atualizar grafico">
    public ResponseEntity<String> updateGrafico(@PathVariable("id") BigInteger graficoId, @RequestBody Grafico grafico, Principal principal) throws Exception{
        try {
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.update(graficoId, grafico, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    /************************
    *   GRAFICO - SERVICO   *
    *************************/

    @PostMapping("servico")
    //<editor-fold defaultstate="collapsed" desc="Criar relaciomento grafico/serviço">
    public ResponseEntity<GraficoServico> saveGrafico(@RequestBody GraficoServico graficoServico, Principal principal) throws Exception{
        // Get User by Email.
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(graficoService.saveGraficoServico(graficoServico, autenticado));
    }
    //</editor-fold>
    
    @PostMapping("servico")
    //<editor-fold defaultstate="collapsed" desc="Excluir relacionamento grafico/serviço">
    public ResponseEntity<String> deleteGrafico(@RequestBody GraficoServico graficoServico, Principal principal) throws Exception{
        // Get User by Email.
        Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
        return ResponseEntity.ok(graficoService.deleteGraficoServico(graficoServico, autenticado).toString());
    }
    //</editor-fold>
    
}