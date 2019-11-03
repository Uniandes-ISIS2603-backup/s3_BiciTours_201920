/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.EventoDTO;
import co.edu.uniandes.csw.bicitours.ejb.EventoLogic;
import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
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
 * @author Estudiante
 */
@Path("eventos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EventoResource {

    private static final String NOEXISTE=" no existe.";
    @Inject
    private EventoLogic eventoLogic;
    
    @POST
    public EventoDTO createEvento(@PathParam("tourId")Long tourId, EventoDTO evento) throws BusinessLogicException
    {

        return new EventoDTO(eventoLogic.createEventoEntity(evento.toEntity(), tourId));
    }
    
    @GET
    public List<EventoDTO> getEventos(@PathParam("tourId") Long tourId) {

        return listEntity2DTO(eventoLogic.getEventos(tourId));
    }
    
    @GET
    @Path("{eventoId: \\d+}")
    public EventoDTO getComentario(@PathParam("tourId") Long tourId, @PathParam("eventoId") Long eventoId) 
    {
        EventoEntity entity = eventoLogic.getEvento(eventoId, tourId);
        if (entity == null) 
        {
            throw new WebApplicationException("El recurso /Evento/" + eventoId + NOEXISTE, 404);
        }

        return new EventoDTO(entity);
    }

    @PUT
    @Path("{eventoId: \\d+}")
    public EventoDTO updateEvento(@PathParam("tourId")Long tourId, @PathParam("eventoId") Long eventoId, EventoDTO evento) throws BusinessLogicException 
    {
        if (eventoId.equals(evento.getId())) {
            throw new BusinessLogicException("Los ids del Review no coinciden.");
        }
        EventoEntity entity = eventoLogic.getEvento(tourId, eventoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /tour/" + tourId + "/evento/" + eventoId + NOEXISTE, 404);

        }

        
        return new EventoDTO(eventoLogic.updateEventoEntity(evento.toEntity()));

    }

    @DELETE
    @Path("{eventoId: \\d+}")
    public void deleteEvento(@PathParam("tourId")Long tourId, @PathParam("eventoId") Long eventoId) throws BusinessLogicException 
    {
        EventoEntity evento = eventoLogic.getEvento(eventoId, tourId);
        if (evento == null) 
        {
            throw new WebApplicationException("El recurso /Evento/" + eventoId + NOEXISTE, 404);
        }
        eventoLogic.deleteEvento(eventoId);
    }
    
    private List<EventoDTO> listEntity2DTO(List<EventoEntity> entityList) 
    {
        List<EventoDTO> list = new ArrayList<>();
        for (EventoEntity evento : entityList) 
        {
            list.add(new EventoDTO(evento));
        }
        return list;
    }
    
}
