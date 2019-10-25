/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.BlogDTO;
import co.edu.uniandes.csw.bicitours.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.SiguienteBlogLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
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
@Path("blogs/{blogsId: \\d+}/siguiente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SiguienteBlogResource {
    @Inject
    private SiguienteBlogLogic siguienteBlogLogic;

    @Inject
    private BlogLogic blogLogic;

    @GET
    public BlogDetailDTO getSiguiente(@PathParam("blogsId") Long blogsId) {
        BlogEntity blogEntity = siguienteBlogLogic.getSiguiente(blogsId);
        if (blogEntity == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + "/blog no existe.", 404);
        }
        BlogDetailDTO blogDetailDTO = new BlogDetailDTO(blogEntity);
        return blogDetailDTO;
    }

    @PUT
    public BlogDetailDTO replaceSiguiente(@PathParam("blogsId") Long blogsId, BlogDTO blog) {
        if (blogLogic.getBlog(blogsId) == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + " no existe.", 404);
        }
        if (blogLogic.getBlog(blog.getId()) == null) {
            throw new WebApplicationException("El recurso /blogs/" + blog.getId() + " no existe.", 404);
        }
        BlogDetailDTO blogDetailDTO = new BlogDetailDTO(siguienteBlogLogic.replaceSiguiente(blogsId, blog.getId()));
        return blogDetailDTO;
    }

    @DELETE
    public void removeSiguiente(@PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        siguienteBlogLogic.removeSiguiente(blogsId);
    }
}
