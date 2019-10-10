/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.TourDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.BlogCreadorLogic;
import co.edu.uniandes.csw.bicitours.ejb.BlogTourLogic;
import co.edu.uniandes.csw.bicitours.ejb.TourLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlogTourResource {
        @Inject
    private BlogTourLogic blogTourLogic;

    @Inject
    private TourLogic tourLogic;

    @GET
    public TourDetailDTO getTour(@PathParam("blogsId") Long blogsId) {
        TourEntity usuarioEntity = blogTourLogic.getTour(blogsId);
        if (usuarioEntity == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + "/blog no existe.", 404);
        }
        TourDetailDTO usuarioDetailDTO = new TourDetailDTO(usuarioEntity);
        return usuarioDetailDTO;
    }

    @PUT
    @Path("{toursId: \\d+}")
    public TourDetailDTO replaceCreador(@PathParam("blogsId") Long blogsId, @PathParam("toursId") Long toursId) {
        if (tourLogic.getTour(toursId) == null) {
            throw new WebApplicationException("El recurso /tours/" + toursId + " no existe.", 404);
        }
        TourDetailDTO tourDetailDTO = new TourDetailDTO(blogTourLogic.replaceTour(blogsId, toursId));
        return tourDetailDTO;
    }

    @DELETE
    public void removeCreador(@PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        blogTourLogic.removeTour(blogsId);
    }
}
