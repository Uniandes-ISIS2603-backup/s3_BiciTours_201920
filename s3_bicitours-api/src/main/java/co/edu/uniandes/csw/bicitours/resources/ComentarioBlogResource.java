/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.bicitours.dtos.BlogDTO;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioBlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
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

@Path("comentarios/{comentariosId: \\d+}/blog")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ComentarioBlogResource {
    @Inject
    private ComentarioLogic comentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ComentarioBlogLogic comentarioBlogLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private BlogLogic blogLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Blog asociada a un Comentario.
     *
     * @param comentariosId Identificador del libro que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param blog La blog que se será del libro.
     * @return JSON {@link ComentarioDetailDTO} - El arreglo de libros guardado en la
     * blog.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la blog o el
     * libro.
     */
    @PUT
    public ComentarioDetailDTO replaceBlog(@PathParam("comentariosId") Long comentariosId, BlogDTO blog) {
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException("El recurso /comentarios/" + comentariosId + " no existe.", 404);
        }
        if (blogLogic.getBlog(blog.getId()) == null) {
            throw new WebApplicationException("El recurso /blogs/" + blog.getId() + " no existe.", 404);
        }
        ComentarioDetailDTO comentarioDetailDTO = new ComentarioDetailDTO(comentarioBlogLogic.replaceBlog(comentariosId, blog.getId()));
        return comentarioDetailDTO;
    }
}
