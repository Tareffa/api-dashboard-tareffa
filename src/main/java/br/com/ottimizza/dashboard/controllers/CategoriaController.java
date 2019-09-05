package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.usuarios.Usuario;
import br.com.ottimizza.dashboard.repositories.usuarios.UsuarioRepository;
import br.com.ottimizza.dashboard.services.CategoriaService;
import java.security.Principal;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("categorias")
public class CategoriaController {
    
    @Context
    HttpServletRequest context;
    
    @Inject
    UsuarioRepository usuarioRepository;
    
    @GET
    //<editor-fold defaultstate="collapsed" desc="Busca categorias">
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias(@QueryParam("description") String descricao,Principal principal) {
        try {
            // Get User by Email.
            Usuario autenticado = usuarioRepository.findByEmail(principal.getName());
            CategoriaService categoriaService = new CategoriaService();

            JSONObject response = categoriaService.getCategorias(descricao, autenticado);
            return Response.ok(response.toString()).build();
        } catch (Exception e) {
            return Response.ok(e.getMessage()).build();
        }
    }
    //</editor-fold>
    
}
