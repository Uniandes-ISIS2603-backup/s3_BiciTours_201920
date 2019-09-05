/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Michel Succar
 * 
 */
@Stateless
public class EventoPersistence {
    
    @PersistenceContext(unitName = "bicitoursPU")
    
    protected EntityManager em;
    
    private static final Logger LOGGER= Logger.getLogger(EventoPersistence.class.getName());
    
    public EventoEntity create(EventoEntity pEvento)
    {
        LOGGER.log(Level.INFO, "Creando un evento nuevo");
        em.persist(pEvento);
        LOGGER.log(Level.INFO, "Evento creado");
        return pEvento;
    }
    
    public EventoEntity find(Long eventosId)
    {
        return em.find(EventoEntity.class, eventosId);
    }
    
    public List<EventoEntity> findAll( )
    {
        TypedQuery<EventoEntity> query = em.createQuery("select u from EventoEntity u", EventoEntity.class);
        return query.getResultList( );
    }
    
    /**
     *
     * @param eventoEntity
     * @return
     */
    public EventoEntity update(EventoEntity eventoEntity)
    {
        return em.merge(eventoEntity);
    }
    
    public void delete(Long eventoId)
    {
        EventoEntity eventoEntity = find(eventoId);
        em.remove(eventoEntity);
    }
}
