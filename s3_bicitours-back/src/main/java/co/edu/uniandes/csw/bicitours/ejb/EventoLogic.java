/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.EventoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Michel Succar Medina
 */
@Stateless
public class EventoLogic {
    
    @Inject
    private EventoPersistence persistence;
    
    public EventoEntity createEventoEntity(EventoEntity evento) throws BusinessLogicException
    {
        if(evento.getNombre() == null)
        {
            throw new BusinessLogicException("El nombre del evento está vacío");
        }
        evento = persistence.create(evento);
        return evento;
    }
    
    
    
}
