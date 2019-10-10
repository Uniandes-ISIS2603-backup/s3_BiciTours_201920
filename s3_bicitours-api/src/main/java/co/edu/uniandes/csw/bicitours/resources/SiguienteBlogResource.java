/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.SiguienteBlogLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
public class SiguienteBlogResource {
    @Inject
    private SiguienteBlogLogic siguienteBlogLogic;

    @Inject
    private BlogLogic blogLogic;

    @GET
    public BlogDetailDTO getAnterior(@PathParam("blogsId") Long blogsId) {
        BlogEntity blogEntity = siguienteBlogLogic.getSiguiente(blogsId);
        if (blogEntity == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + "/blog no existe.", 404);
        }
        BlogDetailDTO blogDetailDTO = new BlogDetailDTO(blogEntity);
        return blogDetailDTO;
    }

    @PUT
    @Path("{blogsId: \\d+}")
    public BlogDetailDTO replaceAnterior(@PathParam("blogsId") Long blogsId, @PathParam("blogsId2") Long blogsId2) {
        if (blogLogic.getBlog(blogsId2) == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + " no existe.", 404);
        }
        BlogDetailDTO blogDetailDTO = new BlogDetailDTO(siguienteBlogLogic.replaceSiguiente(blogsId, blogsId2));
        return blogDetailDTO;
    }

    @DELETE
    public void removeAnterior(@PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        siguienteBlogLogic.removeSiguiente(blogsId);
    }
}
