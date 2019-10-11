/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.ejb.BlogCreadorLogic;
import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
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
@Path("blogs/{blogsId: \\d+}/creador")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlogCreadorResource {
    @Inject
    private BlogCreadorLogic blogCreadorLogic;

    @Inject
    private UsuarioLogic usuarioLogic;

    /**@GET
    public UsuarioDetailDTO getCreador(@PathParam("blogsId") Long blogsId) {
        UsuarioEntity usuarioEntity = blogCreadorLogic.getCreador(blogsId);
        if (usuarioEntity == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + "/blog no existe.", 404);
        }
        UsuarioDetailDTO usuarioDetailDTO = new UsuarioDetailDTO(usuarioEntity);
        return usuarioDetailDTO;
    }

    @PUT
    @Path("{usuariosId: \\d+}")
    public UsuarioDetailDTO replaceCreador(@PathParam("blogsId") Long blogsId, @PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getCreador(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO usuarioDetailDTO = new UsuarioDetailDTO(blogCreadorLogic.replaceCreador(blogsId, usuariosId));
        return usuarioDetailDTO;
    }

    @DELETE
    public void removeCreador(@PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        blogCreadorLogic.removeCreador(blogsId);
    }*/   
}
