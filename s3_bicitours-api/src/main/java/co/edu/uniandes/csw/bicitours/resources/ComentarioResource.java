/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.ComentarioDTO;
import co.edu.uniandes.csw.bicitours.dtos.ComentarioDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioUsuarioLogic;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author JuanRueda
 */

@Path("comentarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class ComentarioResource 
{

    @Inject
    private ComentarioLogic logica;
    
    @Inject
    private ComentarioUsuarioLogic comentarioUsuarioLogica;

    @POST
    public ComentarioDTO createComentario(ComentarioDTO comentario) throws BusinessLogicException 
    {
        ComentarioDTO respuesta = new ComentarioDTO(logica.createComentario(comentario.toEntity()));
        return respuesta;
    }

    @GET
    public List<ComentarioDetailDTO> getComentarios() {
        List<ComentarioDetailDTO> comentarios = listEntity2DetailDTO(logica.getComentarios());
        return comentarios;
    }

    
    @GET
    @Path("{comentarioId: \\d+}")
    public ComentarioDetailDTO getComentario(@PathParam("comentarioId") Long comentarioId) 
    {
        ComentarioEntity comentario = logica.getComentario(comentarioId);
        if (comentario == null) 
        {
            throw new WebApplicationException("El recurso /Comentarios/" + comentarioId + " no existe.", 404);
        }
        ComentarioDetailDTO comentarios = new ComentarioDetailDTO(comentario);
        return comentarios;
    }

    @PUT
    @Path("{comentarioId: \\d+}")
    public ComentarioDetailDTO updateComentario(@PathParam("comentarioId") Long comentarioId, ComentarioDetailDTO comentarios) throws BusinessLogicException 
    {
        comentarios.setId(comentarioId);
        if (logica.getComentario(comentarioId) == null) 
        {
            throw new WebApplicationException("El recurso /Comentarios/" + comentarioId + " no existe.", 404);
        }
        ComentarioDetailDTO detailDTO = new ComentarioDetailDTO(logica.updateComentario(comentarios.toEntity()));
        return detailDTO;
    }

    @DELETE
    @Path("{comentarioId: \\d+}")
    public void deleteComentario(@PathParam("comentarioId") Long comentarioId) throws BusinessLogicException 
    {
        ComentarioEntity comentario = logica.getComentario(comentarioId);
        if (comentario == null) 
        {
            throw new WebApplicationException("El recurso /Comentarios/" + comentarioId + " no existe.", 404);
        }
        comentarioUsuarioLogica.removeUsuario(comentarioId);
        logica.deleteComentario(comentarioId);
    }

    /**
     * Conexión con el servicio de reseñas para un libro. {@link ReviewResource}
     *
     * Este método conecta la ruta de /Comentarios con las rutas de /reviews que
     * dependen del libro, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param comentarioId El ID del libro con respecto al cual se accede al
     * servicio.
     * @return El servicio de Reseñas para ese libro en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     
    @Path("{comentarioId: \\d+}/reviews")
    public Class<ReviewResource> getReviewResource(@PathParam("comentarioId") Long comentarioId) {
        if (ComentarioLogic.getComentario(comentarioId) == null) {
            throw new WebApplicationException("El recurso /Comentarios/" + comentarioId + "/reviews no existe.", 404);
        }
        return ReviewResource.class;
    }

    /**
     * Conexión con el servicio de autores para un libro.
     * {@link ComentarioAuthorsResource}
     *
     * Este método conecta la ruta de /Comentarios con las rutas de /authors que
     * dependen del libro, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param comentarioId El ID del libro con respecto al cual se accede al
     * servicio.
     * @return El servicio de autores para ese libro en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     
    @Path("{comentarioId: \\d+}/authors")
    public Class<ComentarioAuthorsResource> getComentarioAuthorsResource(@PathParam("comentarioId") Long comentarioId) {
        if (ComentarioLogic.getComentario(comentarioId) == null) {
            throw new WebApplicationException("El recurso /Comentarios/" + comentarioId + " no existe.", 404);
        }
        return ComentarioAuthorsResource.class;
    }
    */

    private List<ComentarioDetailDTO> listEntity2DetailDTO(List<ComentarioEntity> entityList) 
    {
        List<ComentarioDetailDTO> list = new ArrayList<>();
        for (ComentarioEntity comentario : entityList) 
        {
            list.add(new ComentarioDetailDTO(comentario));
        }
        return list;
    }
}
