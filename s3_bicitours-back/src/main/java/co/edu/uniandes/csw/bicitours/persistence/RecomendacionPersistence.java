/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.RecomendacionEntity;
import java.util.List;

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
    

    
    public RecomendacionEntity create(RecomendacionEntity pRecomendacion)
    {

        em.persist(pRecomendacion);

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
