/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.EventoPersistence;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Michel Succar Medina
 */
@Stateless
public class EventoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(EventoLogic.class.getName());
    
    @Inject
    private EventoPersistence eventoPersistence;
    
    @Inject
    private TourPersistence tourPersistence;
    
    //Se manejan los permisos de creacion, actualizacion y borrado de eventos en el front, a un usuario no administrador no le apareceran estas opciones.
    
    public EventoEntity createEventoEntity(EventoEntity evento, Long tourId) throws BusinessLogicException
    {
        TourEntity tour = tourPersistence.find(tourId);
        evento.setTour(tour);
        
        if(evento.getNombre() == null)
        {
            throw new BusinessLogicException("El nombre del evento está vacío");
        }
        if(evento.getTour() == null || tourId == null)
        {
            throw new BusinessLogicException("El evento debe estar asignado a un tour");
        }
        if(evento.getHoraInicio() == null || evento.getHoraFin() == null || evento.getHoraInicio() < (new Date().getTime()))
        {
            throw new BusinessLogicException("El evento no puede no tener hora de inicio o final o empezar antes de la fecha actual.");
        }
        if( (evento.getHoraInicio().compareTo(evento.getHoraFin())) >= 0)
        {
            throw new BusinessLogicException("El evento no puede iniciar despues de terminado");
        }
        if(evento.getDescripcion() == null || evento.getDescripcion( ).length( ) <= 0 || evento.getDescripcion().length() > 250 || evento.getDescripcion().equals(""))
        {
            throw new BusinessLogicException("El evento debe tener un descripcion y no puede ser mayor a 150 caracteres.");
        }
        List<EventoEntity> eventos = evento.getTour().getEventosTour();
        for(int i = 0; i < eventos.size(); i++)
        {
            if((eventos.get(i).getHoraInicio()).compareTo(evento.getHoraInicio()) >= 0 
                    && (eventos.get(i).getHoraInicio()).compareTo(evento.getHoraFin()) < 0)
            {
                throw new BusinessLogicException("No se puede crear el evento, pues ya hay un evento programado en esta fecha");
            }
            if((eventos.get(i).getHoraFin()).compareTo(evento.getHoraInicio()) >= 0 
                    && (eventos.get(i).getHoraFin()).compareTo(evento.getHoraFin()) < 0)
            {
                throw new BusinessLogicException("No se puede crear el evento, pues ya hay un evento programado en esta fecha");
            }
        }
        // || fechaFinalTour.compareTo(evento.getHoraFin()) < 0
        long fechaF = evento.getTour().getFecha().getTime()+(evento.getTour().getDuracion()*60000);
        Date fechaFinalTour = new Date(fechaF);
        if(evento.getTour().getFecha().getTime() > evento.getHoraInicio())
        {
            throw new BusinessLogicException("No se puede crear un evento que no ocurra durante el tour.");
        }
        evento = eventoPersistence.create(evento);
        return evento;
    }
    
    public List<EventoEntity> getEventos(Long tourId) 
    {
        TourEntity tourEntity = tourPersistence.find(tourId);
        return tourEntity.getEventosTour();
    }
    
    public EventoEntity getEvento(Long eventoId, Long tourId) 
    {
        EventoEntity comentario = eventoPersistence.find(tourId, eventoId);
        if (comentario == null) 
        {
            LOGGER.log(Level.SEVERE, "El evento con el id = {0} no existe", eventoId);
        }
        return comentario;
    }
    
    public EventoEntity updateEventoEntity(EventoEntity evento, Long tourId) throws BusinessLogicException
    {
        TourEntity tour = tourPersistence.find(tourId);
        evento.setTour(tour);
        
        if(evento.getNombre() == null)
        {
            throw new BusinessLogicException("El nombre del evento está vacío");
        }
        if(evento.getHoraInicio() == null || evento.getHoraFin() == null || evento.getHoraInicio() < new Date().getTime())
        {
            throw new BusinessLogicException("El evento no puede no tener hora de inicio o final o empezar antes de la fecha actual.");
        }
        if( (evento.getHoraInicio().compareTo(evento.getHoraFin())) >= 0)
        {
            throw new BusinessLogicException("El evento no puede iniciar despues de terminado");
        }
        if(evento.getDescripcion() == null || evento.getDescripcion( ).length( ) <= 0 || evento.getDescripcion().length() > 250 || evento.getDescripcion().equals(""))
        {
            throw new BusinessLogicException("El evento debe tener un descripcion y no puede ser mayor a 150 caracteres.");
        }
        List<EventoEntity> eventos = eventoPersistence.findAll();
        for(int i = 0; i < eventoPersistence.findAll().size(); i++)
        {
            if((eventos.get(i).getHoraInicio()).compareTo(evento.getHoraInicio()) >= 0 
                    && (eventos.get(i).getHoraInicio()).compareTo(evento.getHoraFin()) < 0)
            {
                throw new BusinessLogicException("No se puede crear el evento, pues ya hay un evento programado en esta fecha");
            }
            if((eventos.get(i).getHoraFin()).compareTo(evento.getHoraInicio()) >= 0 
                    && (eventos.get(i).getHoraFin()).compareTo(evento.getHoraFin()) < 0)
            {
                throw new BusinessLogicException("No se puede crear el evento, pues ya hay un evento programado en esta fecha");
            }
        }
        evento = eventoPersistence.create(evento);
        return evento;
    }
    
    public void deleteEvento(Long eventoId, Long tourId) throws BusinessLogicException 
    {
        EventoEntity old = getEvento(eventoId, tourId);
        eventoPersistence.delete(eventoId);
    }

    
}
