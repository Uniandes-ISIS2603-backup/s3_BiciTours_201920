/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.FavoritosBlogLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Path("usuarios/{usuariosId: \\d+}/favoritos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FavoritosBlogResource {

    private static final String RECURSO = "El recurso /blogs/";
    private static final String NOEXISTE = " no existe.";
    @Inject
    private FavoritosBlogLogic favoritosBlogLogic;

    @Inject
    private BlogLogic blogLogic;

    @POST
    @Path("{blogsId: \\d+}")
    public BlogDetailDTO addFavorito(@PathParam("usuariosId") Long usuariosId, @PathParam("blogsId") Long blogsId) {
        if (blogLogic.getBlog(blogsId) == null) {
            throw new WebApplicationException(RECURSO + blogsId + NOEXISTE, 404);
        }

        return new BlogDetailDTO(favoritosBlogLogic.addFavorito(blogsId, usuariosId));
    }

    @GET
    public List<BlogDetailDTO> getFavoritos(@PathParam("usuariosId") Long usuariosId) {

        return blogsListEntity2DTOFavoritosBlog(favoritosBlogLogic.getFavoritos(usuariosId));
    }

    @GET
    @Path("{blogsId: \\d+}")
    public BlogDetailDTO getFavorito(@PathParam("usuariosId") Long usuariosId, @PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        if (blogLogic.getBlog(blogsId) == null) {
            throw new WebApplicationException(RECURSO + blogsId + NOEXISTE, 404);
        }

        return new BlogDetailDTO(favoritosBlogLogic.getFavorito(usuariosId, blogsId));
    }

    @PUT
    public List<BlogDetailDTO> replaceFavoritos(@PathParam("usuariosId") Long usuariosId, List<BlogDetailDTO> blogs) {
        for (BlogDetailDTO blog : blogs) {
            if (blogLogic.getBlog(blog.getId()) == null) {
                throw new WebApplicationException(RECURSO + blog.getId() + NOEXISTE, 404);
            }
        }

        return blogsListEntity2DTOFavoritosBlog(favoritosBlogLogic.replaceFavoritos(usuariosId, blogsListDTO2EntityFavoritosBlog(blogs)));
    }

    @DELETE
    @Path("{blogsId: \\d+}")
    public void removeFavorito(@PathParam("usuariosId") Long usuariosId, @PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        if (blogLogic.getBlog(blogsId) == null) {
            throw new WebApplicationException(RECURSO + blogsId + NOEXISTE, 404);
        }
        favoritosBlogLogic.removeFavorito(usuariosId, blogsId);
    }

    private List<BlogDetailDTO> blogsListEntity2DTOFavoritosBlog(List<BlogEntity> entityList) {
        List<BlogDetailDTO> list = new ArrayList<>();
        for (BlogEntity entity : entityList) {
            list.add(new BlogDetailDTO(entity));
        }
        return list;
    }

    private List<BlogEntity> blogsListDTO2EntityFavoritosBlog(List<BlogDetailDTO> dtos) {
        List<BlogEntity> list = new ArrayList<>();
        for (BlogDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
