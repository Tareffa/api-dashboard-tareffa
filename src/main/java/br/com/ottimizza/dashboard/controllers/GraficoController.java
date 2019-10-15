package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.graficos.Grafico;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristica;
import br.com.ottimizza.dashboard.models.graficos.grafico_caracteristica.GraficoCaracteristicaID;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServico;
import br.com.ottimizza.dashboard.models.graficos.grafico_servico.GraficoServicoID;
import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;
import br.com.ottimizza.dashboard.services.GraficoService;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<String> buscaGraficoPorId(@PathVariable("id") BigInteger graficoId, Principal principal) throws Exception{
        try {
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.getGraficoById(graficoId, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @GetMapping
    //<editor-fold defaultstate="collapsed" desc="Buscar lista de graficos">
    public ResponseEntity<String> buscaGraficos(Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.getListGraficos(autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @PostMapping
    //<editor-fold defaultstate="collapsed" desc="Criar grafico">
    public ResponseEntity<String> saveGrafico(@RequestBody Grafico grafico, Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.save(grafico, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
    
    //*************************
    //*   GRAFICO - SERVICO   *
    //*************************

    @GetMapping("{id}/servico")
    //<editor-fold defaultstate="collapsed" desc="Buscar serviços relacionados ao gráfico Id">
    public ResponseEntity<String> buscaServicosRelacionadosGraficoId(
            @PathVariable("id") BigInteger graficoId,
            @RequestParam(value="description", required = false) String descricao,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.buscaServicosRelacionadosGraficoId(graficoId, descricao, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>

    @GetMapping("{id}/servico/faltantes")
    //<editor-fold defaultstate="collapsed" desc="Buscar serviços relacionados ao gráfico Id">
    public ResponseEntity<String> buscaServicosFaltantesGraficoId(
            @PathVariable("id") BigInteger graficoId,
            @RequestParam(value="description", required = false) String descricao,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.buscaServicosFaltantesGraficoId(graficoId, descricao, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>

    @PostMapping("{graficoId}/servico/{servicoId}")
    //<editor-fold defaultstate="collapsed" desc="Criar relacionamento grafico/serviço">
    public ResponseEntity<String> saveGraficoServico(
            @PathVariable("graficoId") Long graficoId,
            @PathVariable("servicoId") Long servicoId,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            
            //GRAFICO/SERVICO ID
            GraficoServico graficoServico = new GraficoServico();
            GraficoServicoID graficoServicoId = new GraficoServicoID();
            graficoServicoId.setGraficoId(graficoId);
            graficoServicoId.setServicoId(servicoId);
            graficoServico.setId(graficoServicoId);
            
            return ResponseEntity.ok(graficoService.saveGraficoServico(graficoServico, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @DeleteMapping("{graficoId}/servico/{servicoId}")
    //<editor-fold defaultstate="collapsed" desc="Excluir relacionamento grafico/serviço">
    public ResponseEntity<String> deleteGraficoServico(
            @PathVariable("graficoId") Long graficoId,
            @PathVariable("servicoId") Long servicoId,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            
            //GRAFICO/SERVICO ID
            GraficoServico graficoServico = new GraficoServico();
            GraficoServicoID graficoServicoId = new GraficoServicoID();
            graficoServicoId.setGraficoId(graficoId);
            graficoServicoId.setServicoId(servicoId);
            graficoServico.setId(graficoServicoId);
            
            return ResponseEntity.ok(graficoService.deleteGraficoServico(graficoServico, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>

    //********************************
    //*   GRAFICO - CARACTERISTICA   *
    //********************************
    
    @GetMapping("{id}/caracteristica")
    //<editor-fold defaultstate="collapsed" desc="Buscar características relacionados ao gráfico Id">
    public ResponseEntity<String> buscaCaracteristicasRelacionadosGraficoId(
            @PathVariable("id") BigInteger graficoId, 
            @RequestParam(value = "description", required = false) String descricao,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.buscaCaracteristicasRelacionadosGraficoId(graficoId, descricao, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @GetMapping("{id}/caracteristica/faltantes")
    //<editor-fold defaultstate="collapsed" desc="Buscar características relacionados ao gráfico Id">
    public ResponseEntity<String> buscaCaracteristicasFaltantesGraficoId(
            @PathVariable("id") BigInteger graficoId,
            @RequestParam(value = "description", required = false) String descricao,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            return ResponseEntity.ok(graficoService.buscaCaracteristicasFaltantesGraficoId(graficoId, descricao, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @PostMapping("{graficoId}/caracteristica/{caracteristicaId}")
    //<editor-fold defaultstate="collapsed" desc="Criar relacionamento grafico/caracteristica">
    public ResponseEntity<String> saveGraficoCaracteristica(
            @PathVariable("graficoId") Long graficoId,
            @PathVariable("caracteristicaId") Long caracteristicaId,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            
            //GRAFICO/CARACTERISTICA ID
            GraficoCaracteristica graficoCaracteristica = new GraficoCaracteristica();
            GraficoCaracteristicaID graficoCaracteristicaId = new GraficoCaracteristicaID();
            graficoCaracteristicaId.setGraficoId(graficoId);
            graficoCaracteristicaId.setCaracteristicaId(caracteristicaId);
            graficoCaracteristica.setId(graficoCaracteristicaId);
            
            return ResponseEntity.ok(graficoService.saveGraficoCaracteristica(graficoCaracteristica, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
    
    @DeleteMapping("{graficoId}/caracteristica/{caracteristicaId}")
    //<editor-fold defaultstate="collapsed" desc="Excluir relacionamento grafico/caracteristica">
    public ResponseEntity<String> deleteGraficoCaracteristica(
            @PathVariable("graficoId") Long graficoId,
            @PathVariable("caracteristicaId") Long caracteristicaId,
            Principal principal) throws Exception{
        try{
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            
            //GRAFICO/CARACTERISTICA ID
            GraficoCaracteristica graficoCaracteristica = new GraficoCaracteristica();
            GraficoCaracteristicaID graficoCaracteristicaId = new GraficoCaracteristicaID();
            graficoCaracteristicaId.setGraficoId(graficoId);
            graficoCaracteristicaId.setCaracteristicaId(caracteristicaId);
            graficoCaracteristica.setId(graficoCaracteristicaId);
            
            return ResponseEntity.ok(graficoService.deleteGraficoCaracteristica(graficoCaracteristica, autenticado).toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    //</editor-fold>
}