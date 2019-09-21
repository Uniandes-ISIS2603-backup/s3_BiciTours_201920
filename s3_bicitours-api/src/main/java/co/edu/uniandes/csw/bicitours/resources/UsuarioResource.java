/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.UsuarioDTO;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *
 * @author Juan Sebastián González Rojas
 */
@Path("usuario")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class UsuarioResource {
    
    private final static Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    
    @POST
    public UsuarioDTO crearCuenta (UsuarioDTO usuario){
        return null;
        
    }
}
