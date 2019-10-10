/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.SeguroDTO;
import co.edu.uniandes.csw.bicitours.ejb.SeguroLogic;
import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
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
@Path("cSeguros")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class SeguroResource {
    
    @Inject
    private SeguroLogic logica;

    @POST
    public SeguroDTO createSeguro(SeguroDTO seguro) throws BusinessLogicException 
    {
         SeguroDTO respuesta = new SeguroDTO(logica.createSeguro(seguro.toEntity()));
        return respuesta;
    }

    @GET
    public List<SeguroDTO> getSeguros() {
        
        List<SeguroDTO> listaSeguros = listEntity2DetailDTO(logica.getSeguros());
        return listaSeguros;
    }
 @GET
    @Path("{seguroId: \\d+}")
    public SeguroDTO getSeguro(@PathParam("seguroId") Long seguroId) 
    {
        SeguroEntity seguro = logica.getSeguro(seguroId);
        if (seguro == null) 
        {
            throw new WebApplicationException("El recurso /Seguros/" + seguroId + " no existe.", 404);
        }
        SeguroDTO seguros = new SeguroDTO(seguro);
        return seguros;
    }
    
  
    @PUT
    @Path("{seguroId: \\d+}")
    public SeguroDTO updateSeguro(@PathParam("seguroId") Long seguroId, SeguroDTO seguros) throws BusinessLogicException 
    {
         seguros.setId(seguroId);
        if (logica.getSeguro(seguroId) == null) 
        {
            throw new WebApplicationException("El recurso /Seguros/" + seguroId + " no existe.", 404);
        }
        SeguroDTO DTO = new SeguroDTO(logica.updateSeguro(seguroId, seguros.toEntity()));
        return DTO;
    }

    @DELETE
    @Path("{seguroId: \\d+}")
    public void deleteSeguro(@PathParam("seguroId") Long seguroId) throws BusinessLogicException 
    {
         SeguroEntity seguro = logica.getSeguro(seguroId);
        if (seguro == null) 
        {
            throw new WebApplicationException("El recurso /Seguros/" + seguroId + " no existe.", 404);
        }
        logica.deleteSeguro(seguroId);
    }
    /**
     * Conexión con el servicio de autores para un bicitour.
     * {@link SeguroSegurosResource}
     *
     * Este método conecta la ruta de /Seguros con las rutas de /seguros que
     * dependen del bicitour, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de las reseñas.
     *
     * @param seguroId El ID del bicitour con respecto al cual se accede al
     * servicio.
     * @return El servicio de autores para ese bicitour en paricular.\
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el bicitour.
     
    @Path("{seguroId: \\d+}/seguros")
    public Class<SeguroSegurosResource> getSeguroSegurosResource(@PathParam("seguroId") Long seguroId) {
        if (SeguroLogic.getSeguro(seguroId) == null) {
            throw new WebApplicationException("El recurso /Seguros/" + seguroId + " no existe.", 404);
        }
        return SeguroSegurosResource.class;
    }
    */

    private List<SeguroDTO> listEntity2DetailDTO(List<SeguroEntity> entityList) 
    {
        List<SeguroDTO> list = new ArrayList<>();
        for (SeguroEntity seguro : entityList) 
        {
            list.add(new SeguroDTO(seguro));
        }
        return list;
    }
}
