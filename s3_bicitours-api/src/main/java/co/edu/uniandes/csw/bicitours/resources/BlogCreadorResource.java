/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.UsuarioDTO;
import co.edu.uniandes.csw.bicitours.dtos.UsuarioDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.BlogCreadorLogic;
import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
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
    private static final String RECURSO = "El recurso /blogs/";
    private static final String NOEXISTE = " no existe.";
    @Inject
    private BlogCreadorLogic blogCreadorLogic;

    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private BlogLogic blogLogic;

    @GET
    public UsuarioDetailDTO getCreador(@PathParam("blogsId") Long blogsId) {
        BlogEntity blogEntity = blogLogic.getBlog(blogsId);

        if (blogEntity == null) {
            throw new WebApplicationException(RECURSO + blogsId + NOEXISTE, 404);
        }
        UsuarioEntity usuarioEntity = blogCreadorLogic.getCreador(blogsId);        
        if (usuarioEntity == null) {
            throw new WebApplicationException(RECURSO + blogsId + "/creador no existe.", 404);
        }

        return new UsuarioDetailDTO(usuarioEntity);
    }

    @PUT
    public UsuarioDetailDTO replaceCreador(@PathParam("blogsId") Long blogsId, UsuarioDTO usuario) {
        if (blogLogic.getBlog(blogsId) == null) {
            throw new WebApplicationException(RECURSO + blogsId + NOEXISTE, 404);
        }
        if (usuarioLogic.getUsuario(usuario.getId()) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuario.getId() + NOEXISTE, 404);
        }

        return new UsuarioDetailDTO(blogCreadorLogic.replaceCreador(blogsId, usuario.getId()));
    }

    @DELETE
    public void removeCreador(@PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        blogCreadorLogic.removeCreador(blogsId);
    }
}
