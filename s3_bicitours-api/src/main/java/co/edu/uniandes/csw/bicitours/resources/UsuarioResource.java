/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.UsuarioDTO;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
    @Inject
    UsuarioLogic usuarioLogic;  //El objeto asociado con esta variable sea asignado por el contenedor
    
    private final static Logger LOGGER = Logger.getLogger(UsuarioResource.class.getName());
    
    @POST
    public UsuarioDTO crearUsuario (UsuarioDTO usuario) throws BusinessLogicException{
        UsuarioEntity usuarioEntity = usuario.toEntity();
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.create(usuarioEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        return nuevoUsuarioDTO;        
    }
    
    @GET
    @Path("{usuarioId ://+d}")
    public UsuarioDTO getUsuario(){
        return null;
    }
}
