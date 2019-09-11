/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.RecomendacionEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Maria CLara Noguera
 */
@Stateless
public class RecomendacionPersistence {
    @PersistenceContext(unitName = "bicitoursPU")
    
    protected EntityManager em;
    
    private static final Logger LOGGER= Logger.getLogger(RecomendacionPersistence.class.getName());
    
    public RecomendacionEntity create(RecomendacionEntity pRecomendacion)
    {
        LOGGER.log(Level.INFO, "Creando una recomendacion nueva");
        em.persist(pRecomendacion);
        LOGGER.log(Level.INFO, "Recomendacion creada");
        return pRecomendacion;
    }
    
    public RecomendacionEntity find(Long recomendacionId)
    {
        return em.find(RecomendacionEntity.class, recomendacionId);
    }
    
    public List<RecomendacionEntity> findAll( )
    {
        TypedQuery<RecomendacionEntity> query = em.createQuery("select u from RecomendacionEntity u", RecomendacionEntity.class);
        return query.getResultList( );
    }
    
    /**
     *
     * @param recomendacionEntity
     * @return
     */
    public RecomendacionEntity update(RecomendacionEntity recomendacionEntity)
    {
        return em.merge(recomendacionEntity);
    }
    
    public void delete(Long recomendacionId)
    {
        RecomendacionEntity recomendacionEntity = find(recomendacionId);
        em.remove(recomendacionEntity);
    }
}