/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.bicitours.dtos.TourDTO;
import co.edu.uniandes.csw.bicitours.dtos.TourDetailDTO;

import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.BlogTourLogic;
import co.edu.uniandes.csw.bicitours.ejb.TourLogic;

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
@Path("blogs/{blogsId: \\d+}/tours")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlogTourResource {

    @Inject
    private BlogTourLogic blogTourLogic;

    @Inject
    private TourLogic tourLogic;

    @Inject
    private BlogLogic blogLogic;

    @GET
    public TourDetailDTO getTour(@PathParam("blogsId") Long blogsId) {
        TourEntity usuarioEntity = blogTourLogic.getTour(blogsId);
        if (usuarioEntity == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + "/blog no existe.", 404);
        }

        return new TourDetailDTO(usuarioEntity);
    }

    @PUT
    public BlogDetailDTO replaceTour(@PathParam("blogsId") Long blogsId, TourDTO tour) {
        if (blogLogic.getBlog(blogsId) == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + " no existe.", 404);
        }
        if (tourLogic.getTour(tour.getId()) == null) {
            throw new WebApplicationException("El recurso /tours/" + tour.getId() + " no existe.", 404);
        }

        return new BlogDetailDTO(blogTourLogic.replaceTour(blogsId, tour.getId()));
    }

    @DELETE
    public void removeTour(@PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        blogTourLogic.removeTour(blogsId);
    }
}
