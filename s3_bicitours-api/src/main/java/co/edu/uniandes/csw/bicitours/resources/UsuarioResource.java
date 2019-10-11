/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.UsuarioDTO;
import co.edu.uniandes.csw.bicitours.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

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
    
     /**
     * Crea un nuevo usuario con la información que se recibe en el cuerpo de
     * la petición y se regresa un objeto idéntico con un id auto-generado por
     * la base de datos.
     *
     * @param usuario {@link UsuarioDTO} - La editorial que se desea
     * guardar.
     * @return JSON {@link UsuarioDTO} - La editorial guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el usuario, los parámetros no estan completos o son inválidos.
     */
    @POST
    public UsuarioDTO crearUsuario (UsuarioDTO usuario) throws BusinessLogicException{
        UsuarioEntity usuarioEntity = usuario.toEntity();
        UsuarioEntity nuevoUsuarioEntity = usuarioLogic.create(usuarioEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        UsuarioDTO nuevoUsuarioDTO = new UsuarioDTO(nuevoUsuarioEntity);
        return nuevoUsuarioDTO;        
    }
    
    /**
     * Busca el usuario con el id asociado recibido en la URL y lo devuelve.
     *
     * @param usuarioId Identificador del usuario que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link EditorialDetailDTO} - El usuario buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    @Path("{usuarioId ://+d}")
    public UsuarioDetailDTO getUsuario(@PathParam("usuarioId") Long usuarioId){
        UsuarioEntity usuario= usuarioLogic.getUsuario(usuarioId);
        if(usuario == null)
        {
            throw new WebApplicationException("El recurso /usuario/" + usuarioId + " no existe.", 404);
        }
        return new UsuarioDetailDTO(usuario);
    }
    /**
     * Convierte una lista de entidades a DTO.
     * Este método convierte una lista de objetos UsuarioEntity a una lista de
     * objetos UsuarioDetailDTO (json)
     * @param entityList corresponde a la lista de editoriales de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de editoriales en forma DTO (json)
     */
    private List<UsuarioDetailDTO> listEntity2DetailDTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Retorna todos los usuarios.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial.
     */
    @GET
    public List<UsuarioDetailDTO> getUsuarios(){
        List<UsuarioEntity> usuarios= usuarioLogic.getUsuarios();
        if(usuarios == null)
        {
            throw new WebApplicationException("El recurso /usuario/ esta vacío.", 404);
        }
        return listEntity2DetailDTO(usuarios);
    }
    
    /**
     * Actualiza el usuario con el id recibido en la URL con la información
     * que se recibe en el cuerpo de la petición.
     *
     * @param usuarioId Identificador de la editorial que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param nuevoUsuario {@link UsuarioDetailDTO} El usuario que se desea
     * guardar.
     * @return JSON {@link UsuarioDetailDTO} - El usuario guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la editorial a
     * actualizar.
     */
    @PUT
    @Path("{usuarioId: \\d+}")
    public UsuarioDetailDTO updateUsuario(@PathParam("usuarioId") Long usuarioId, UsuarioDetailDTO nuevoUsuario) throws WebApplicationException {
        nuevoUsuario.setId(usuarioId);
        if (usuarioLogic.getUsuario(usuarioId) == null) {
            throw new WebApplicationException("El recurso /usuario/" + usuarioId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuarioLogic.updateUsuario(usuarioId, nuevoUsuario.toEntity()));
        return detailDTO;
    }

    /**
     * Borra el usuario con el id asociado recibido en la URL.
     * @param usuarioId Identificador de usuario que se desea borrar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el usuario.
     */
    @DELETE
    @Path("{usuarioId: \\d+}")
    public void deleteUsuario(@PathParam("usuarioId") Long usuarioId) throws BusinessLogicException {
        if (usuarioLogic.getUsuario(usuarioId) == null) {
            throw new WebApplicationException("El recurso /usuario/" + usuarioId + " no existe.", 404);
        }
        usuarioLogic.deleteUsuario(usuarioId);
    }
}
