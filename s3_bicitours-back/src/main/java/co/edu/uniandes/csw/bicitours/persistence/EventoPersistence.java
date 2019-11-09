/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import java.util.List;

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

    public EventoEntity create(EventoEntity pEvento) {

        em.persist(pEvento);

        return pEvento;
    }

    public EventoEntity find(Long tourId, Long eventosId) {
        TypedQuery<EventoEntity> q = em.createQuery("select p from EventoEntity p where (p.tour.id = :tourid) and (p.id = :eventosId)", EventoEntity.class);
        q.setParameter("tourid", tourId);
        q.setParameter("eventosId", eventosId);
        List<EventoEntity> results = q.getResultList();
        EventoEntity evento = null;
        if (results == null) {
            evento = null;
        } else if (results.isEmpty()) {
            evento = null;
        } else {
            evento = results.get(0);
        }
        return evento;
    }

    public List<EventoEntity> findAll() {
        TypedQuery<EventoEntity> query = em.createQuery("select u from EventoEntity u", EventoEntity.class);
        return query.getResultList();
    }

    /**
     *
     * @param eventoEntity
     * @return
     */
    public EventoEntity update(EventoEntity eventoEntity) {
        return em.merge(eventoEntity);
    }

    public void delete(Long eventoId) {
        EventoEntity eventoEntity = em.find(EventoEntity.class, eventoId);
        em.remove(eventoEntity);
    }
}
