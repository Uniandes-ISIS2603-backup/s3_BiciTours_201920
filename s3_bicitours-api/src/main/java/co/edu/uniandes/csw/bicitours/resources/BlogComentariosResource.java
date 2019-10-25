/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.BlogComentariosLogic;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
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
@Path("blogs/{blogsId: \\d+}/comentarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BlogComentariosResource {
    @Inject
    private BlogComentariosLogic blogComentariosLogic;

    @Inject
    private ComentarioLogic comentarioLogic;

    @POST
    @Path("{comentariosId: \\d+}")
    public ComentarioDetailDTO addComentario(@PathParam("blogsId") Long blogsId, @PathParam("comentariosId") Long comentariosId) {
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException("El recurso /comentarios/" + comentariosId + " no existe.", 404);
        }
        ComentarioDetailDTO detailDTO = new ComentarioDetailDTO(blogComentariosLogic.addComentario(blogsId, comentariosId));
        return detailDTO;
    }

    @GET
    public List<ComentarioDetailDTO> getComentarios(@PathParam("blogsId") Long blogsId) {
        List<ComentarioDetailDTO> lista = comentariosListEntity2DTO(blogComentariosLogic.getComentarios(blogsId));
        return lista;
    }

    @GET
    @Path("{comentariosId: \\d+}")
    public ComentarioDetailDTO getComentario(@PathParam("blogsId") Long blogsId, @PathParam("comentariosId") Long comentariosId) throws BusinessLogicException {
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException("El recurso /comentarios/" + comentariosId + " no existe.", 404);
        }
        ComentarioDetailDTO detailDTO = new ComentarioDetailDTO(blogComentariosLogic.getComentario(blogsId, comentariosId));
        return detailDTO;
    }

    @PUT
    public List<ComentarioDetailDTO> replaceComentarios(@PathParam("blogsId") Long blogsId, List<ComentarioDetailDTO> comentarios) {
        for (ComentarioDetailDTO comentario : comentarios) {
            if (comentarioLogic.getComentario(comentario.getId()) == null) {
                throw new WebApplicationException("El recurso /comentarios/" + comentario.getId() + " no existe.", 404);
            }
        }
        List<ComentarioDetailDTO> lista = comentariosListEntity2DTO(blogComentariosLogic.replaceComentarios(blogsId, comentariosListDTO2Entity(comentarios)));
        return lista;
    }

    @DELETE
    @Path("{comentariosId: \\d+}")
    public void removeComentario(@PathParam("blogsId") Long blogsId, @PathParam("comentariosId") Long comentariosId) throws BusinessLogicException {
        if (comentarioLogic.getComentario(comentariosId) == null) {
            throw new WebApplicationException("El recurso /comentarios/" + comentariosId + " no existe.", 404);
        }
        blogComentariosLogic.removeComentario(blogsId, comentariosId);
    }

    private List<ComentarioDetailDTO> comentariosListEntity2DTO(List<ComentarioEntity> entityList) {
        List<ComentarioDetailDTO> list = new ArrayList<>();
        for (ComentarioEntity entity : entityList) {
            list.add(new ComentarioDetailDTO(entity));
        }
        return list;
    }

    private List<ComentarioEntity> comentariosListDTO2Entity(List<ComentarioDetailDTO> dtos) {
        List<ComentarioEntity> list = new ArrayList<>();
        for (ComentarioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
