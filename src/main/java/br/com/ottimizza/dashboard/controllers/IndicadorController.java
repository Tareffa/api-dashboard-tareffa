package br.com.ottimizza.dashboard.controllers;

import java.math.BigInteger;
import java.security.Principal;

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

import br.com.ottimizza.dashboard.models.indicadores.Indicador;
import br.com.ottimizza.dashboard.models.servicos.ServicoProgramadoFiltroAvancado;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;
import br.com.ottimizza.dashboard.services.IndicadorService;

@RestController
@RequestMapping("/indicador")
public class IndicadorController {
    
    @Context
    HttpServletRequest context;
    
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    IndicadorService indicadorService;
    
    //*************************
    //*         CRUD          *
    //*************************
    
    @GetMapping("{id}")
    //<editor-fold defaultstate="collapsed" desc="Buscar indicador por id">
    public ResponseEntity<String> buscaIndicadorPorId(@PathVariable("id") BigInteger indicadorId, Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(indicadorService.getIndicadorById(indicadorId, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @GetMapping
    //<editor-fold defaultstate="collapsed" desc="Buscar lista de indicadores">
    public ResponseEntity<String> buscaIndicadores(Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(indicadorService.getListIndicadores(autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @PostMapping
    //<editor-fold defaultstate="collapsed" desc="Criar indicador">
    public ResponseEntity<String> saveIndicador(@RequestBody Indicador indicador, Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(indicadorService.save(indicador, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @DeleteMapping("{id}")
    //<editor-fold defaultstate="collapsed" desc="Excluir indicador">
    public ResponseEntity<String> deleteIndicador(@PathVariable("id") BigInteger indicadorId, Principal principal) throws Exception{
        try {
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(indicadorService.delete(indicadorId, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @PutMapping("{id}")
    //<editor-fold defaultstate="collapsed" desc="Atualizar indicador">
    public ResponseEntity<String> updateIndicador(@PathVariable("id") BigInteger indicadorId, @RequestBody Indicador indicador, Principal principal) throws Exception{
        try {
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(indicadorService.update(indicadorId, indicador, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>

    //*************************
    //*        GRAFICO        *
    //*************************
    
    @GetMapping("{id}/grafico")
    //<editor-fold defaultstate="collapsed" desc="Buscar graficos relacionados ao indicador por id">
    public ResponseEntity<String> buscaGraficosDoIndicadorPorId(@PathVariable("id") BigInteger indicadorId, Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(indicadorService.getListGraphicFromIndicadores(indicadorId, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>   
    
    //*************************
    //*   GRAFICO - SERVICO   *
    //*************************
    
    @PostMapping("{id}/servico/programado/count")
    //<editor-fold defaultstate="collapsed" desc="Buscar serviços relacionados ao gráfico Id">
    public ResponseEntity<String> countServicoProgramado(
            @PathVariable("id") BigInteger indicadorId,
            @RequestBody ServicoProgramadoFiltroAvancado filtro,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(indicadorService.countServicoProgramado(indicadorId, filtro, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @GetMapping("/tareffa")
    public ResponseEntity<?> getIndicadoresTareffa() {
    	return ResponseEntity.ok(indicadorService.getIndicadoresTareffa());
    }
}