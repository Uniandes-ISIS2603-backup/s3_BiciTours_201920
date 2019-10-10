/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.RecomendacionDTO;
import co.edu.uniandes.csw.bicitours.ejb.RecomendacionLogic;
import co.edu.uniandes.csw.bicitours.entities.RecomendacionEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
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

/**
 *
 * @author Maria Clara Noguera Echeverri
 */
@Path("cRecomendacions")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class RecomendacionResource {
    
    @Inject
    private RecomendacionLogic logica;

    @POST
    public RecomendacionDTO createRecomendacion(RecomendacionDTO recomendacion) throws BusinessLogicException 
    {
         RecomendacionDTO respuesta = new RecomendacionDTO(logica.createRecomendacion(recomendacion.toEntity()));
        return respuesta;
    }

    @GET
    public List<RecomendacionDTO> getRecomendacions() {
        
        List<RecomendacionDTO> listaRecomendacions = listEntity2DetailDTO(logica.getRecomendacions());
        return listaRecomendacions;
    }
 @GET
    @Path("{recomendacionId: \\d+}")
    public RecomendacionDTO getRecomendacion(@PathParam("recomendacionId") Long recomendacionId) 
    {
        RecomendacionEntity recomendacion = logica.getRecomendacion(recomendacionId);
        if (recomendacion == null) 
        {
            throw new WebApplicationException("El recurso /Recomendacions/" + recomendacionId + " no existe.", 404);
        }
        RecomendacionDTO recomendacions = new RecomendacionDTO(recomendacion);
        return recomendacions;
    }
    
  
    @PUT
    @Path("{recomendacionId: \\d+}")
    public RecomendacionDTO updateRecomendacion(@PathParam("recomendacionId") Long recomendacionId, RecomendacionDTO recomendacions) throws BusinessLogicException 
    {
         recomendacions.setId(recomendacionId);
        if (logica.getRecomendacion(recomendacionId) == null) 
        {
            throw new WebApplicationException("El recurso /Recomendacions/" + recomendacionId + " no existe.", 404);
        }
        RecomendacionDTO DTO = new RecomendacionDTO(logica.updateRecomendacion(recomendacionId, recomendacions.toEntity()));
        return DTO;
    }

    @DELETE
    @Path("{recomendacionId: \\d+}")
    public void deleteRecomendacion(@PathParam("recomendacionId") Long recomendacionId) throws BusinessLogicException 
    {
         RecomendacionEntity recomendacion = logica.getRecomendacion(recomendacionId);
        if (recomendacion == null) 
        {
            throw new WebApplicationException("El recurso /Recomendacions/" + recomendacionId + " no existe.", 404);
        }
        logica.deleteRecomendacion(recomendacionId);
    }
    /**
     * Conexión con el servicio de autores para un bicitour.
     * {@link RecomendacionRecomendacionsResource}
     *
     * Este método conecta la ruta de /Recomendacions con las rutas de /recomendacions que
     * dependen del bicitour, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param recomendacionId El ID del bicitour con respecto al cual se accede al
     * servicio.
     * @return El servicio de autores para ese bicitour en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el bicitour.
     
    @Path("{recomendacionId: \\d+}/recomendacions")
    public Class<RecomendacionRecomendacionsResource> getRecomendacionRecomendacionsResource(@PathParam("recomendacionId") Long recomendacionId) {
        if (RecomendacionLogic.getRecomendacion(recomendacionId) == null) {
            throw new WebApplicationException("El recurso /Recomendacions/" + recomendacionId + " no existe.", 404);
        }
        return RecomendacionRecomendacionsResource.class;
    }
    */

    private List<RecomendacionDTO> listEntity2DetailDTO(List<RecomendacionEntity> entityList) 
    {
        List<RecomendacionDTO> list = new ArrayList<>();
        for (RecomendacionEntity recomendacion : entityList) 
        {
            list.add(new RecomendacionDTO(recomendacion));
        }
        return list;
    }
}

