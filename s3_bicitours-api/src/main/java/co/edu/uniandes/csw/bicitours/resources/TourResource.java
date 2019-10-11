/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.TourDTO;
import co.edu.uniandes.csw.bicitours.dtos.TourDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.TourLogic;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
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
 * @author Jhuliana Barrios
 */
@Path("tours")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class TourResource {
    @Inject
    private TourLogic tourLogic;

    /**
     * Crea un nuevo tour con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con una identificación generada por la
     * base de datos.
     *
     * @param t {@link TourDTO} - EL tour que se desea guardar.
     * @return JSON {@link TourDTO} - El tour guardado con el atributo id
     * autogenerado.
     * @throws co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException si el tour 
     * a crear no cumple con las reglas de negocio.
     */    
    @POST
    public TourDetailDTO createTour(TourDTO t) throws BusinessLogicException {
        TourEntity tourEntity = t.toEntity();
        TourEntity nuevoTourEntity = tourLogic.createTour(tourEntity);
        TourDetailDTO nuevoTourDTO = new TourDetailDTO(nuevoTourEntity);
        return nuevoTourDTO;
    }

    /**
     * Busca y devuelve todos los tours que existen en la aplicacion.
     *
     * @return JSONArray {@link TourDetailDTO} - Los tours encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */    
    @GET
    public List<TourDetailDTO> getTours() {
        
        List<TourDetailDTO> listaTours = new ArrayList<>();
        
        for(TourEntity e : tourLogic.getTours())
        {
            TourDetailDTO nueva = new TourDetailDTO(e);
            listaTours.add(nueva);
        }
        
        return listaTours;
    }
    
    /**
     * Busca el tour con el id asociado recibido en la URL y lo devuelve.
     *
     * @param toursId Identificador del tour que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link TourDetailDTO} - El tour buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tour.
     */    
    @GET
    @Path("{toursId: \\d+}")
    public TourDetailDTO getTour(@PathParam("tourId") long toursId)throws WebApplicationException
    {
        TourEntity entidad = tourLogic.getTour(toursId);
        
        if(entidad == null)
            throw new WebApplicationException("El recurso /tours/" + toursId + " no existe.", 404);
        
        TourDetailDTO tour = new TourDetailDTO(entidad);
        return tour;
        
    }
    
    /**
     * Actualiza el tour con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param toursId Identificador del tour que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param tour {@link TourDTO} El tour que se desea guardar.
     * @return JSON {@link AuthorDTO} - El tour guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el tour a
     * actualizar.
     */    
    @PUT
    @Path("{toursId: \\d+}")
    public TourDetailDTO updateTour(@PathParam("toursId") long toursId, TourDetailDTO tour)
    {
        tour.setId(toursId);
        
        if(tourLogic.getTour(toursId) == null)
            throw new WebApplicationException("El recurso /tours/" + toursId + " no existe.", 404);
        
        TourDetailDTO detail = new TourDetailDTO(tourLogic.updateTour(tour.toEntity()));
        return detail;        
    }
    
    
    /**
     * Borra el tour con el id asociado recibido en la URL.
     *
     * @param toursId Identificador del tour que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el tour a eliminar.
     */
    @DELETE
    @Path("{toursId: \\d+}")
    public void deletoTour(@PathParam("toursId")long toursId)
    {
        if(tourLogic.getTour(toursId)== null)
            throw new WebApplicationException("El recurso /tours/" + toursId + " no existe.", 404);
        tourLogic.deleteTour(toursId);       
    }
}
