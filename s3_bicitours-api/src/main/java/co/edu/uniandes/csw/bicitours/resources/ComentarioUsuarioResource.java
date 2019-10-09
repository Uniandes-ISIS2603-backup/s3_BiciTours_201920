/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.bicitours.dtos.UsuarioDTO;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioUsuarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.mappers.WebApplicationExceptionMapper;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
/**
 *
 * @author JuanRueda
 */

@Path("comentarios/{comentariosId: \\d+}/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ComentarioUsuarioResource {
    @Inject
    private ComentarioLogic comentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ComentarioUsuarioLogic comentarioUsuarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private UsuarioLogic usuarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Usuario asociada a un Comentario.
     *
     * @param comentariosId Identificador del libro que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param usuario La usuario que se será del libro.
     * @return JSON {@link ComentarioDetailDTO} - El arreglo de libros guardado en la
     * usuario.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la usuario o el
     * libro.
     */
    //@PUT
    //public ComentarioDetailDTO replaceUsuario(@PathParam("comentariosId") Long comentariosId, UsuarioDTO usuario) {
        //if (comentarioLogic.getComentario(comentariosId) == null) {
        //    throw new WebApplicationException("El recurso /comentarios/" + comentariosId + " no existe.", 404);
        //}
        //if (usuarioLogic.getUsuario(usuario.getId()) == null) {
        //    throw new WebApplicationException("El recurso /usuarios/" + usuario.getId() + " no existe.", 404);
        //}
        //ComentarioDetailDTO comentarioDetailDTO = new ComentarioDetailDTO(comentarioUsuarioLogic.replaceUsuario(comentariosId, usuario.getId()));
        //return comentarioDetailDTO;
    //}
}
