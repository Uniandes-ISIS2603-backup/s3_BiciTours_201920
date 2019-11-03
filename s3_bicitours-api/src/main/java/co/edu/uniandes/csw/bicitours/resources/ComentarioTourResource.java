/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.bicitours.dtos.TourDTO;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioTourLogic;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.TourLogic;
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

@Path("comentarios/{comentariosId: \\d+}/tour")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ComentarioTourResource {
    @Inject
    private ComentarioLogic comentarioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ComentarioTourLogic comentarioTourLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private TourLogic tourLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Remplaza la instancia de Tour asociada a un Comentario.
     *
     * @param comentariosId Identificador del libro que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param tour La tour que se será del libro.
     * @return JSON {@link ComentarioDetailDTO} - El arreglo de libros guardado en la
     * tour.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la tour o el
     * libro.
     */
    @PUT
    public ComentarioDetailDTO replaceTour(@PathParam("comentariosId") Long comentariosId, TourDTO tour) {
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException("El recurso /comentarios/" + comentariosId + " no existe.", 404);
        }
        if (tourLogic.getTour(tour.getId()) == null) {
            throw new WebApplicationException("El recurso /tours/" + tour.getId() + " no existe.", 404);
        }

        return new ComentarioDetailDTO(comentarioTourLogic.replaceTour(comentariosId, tour.getId()));
    }
}
