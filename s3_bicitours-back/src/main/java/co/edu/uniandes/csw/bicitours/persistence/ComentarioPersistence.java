/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author JuanRueda
 */

@Stateless
public class ComentarioPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ComentarioPersistence.class.getName());
    @PersistenceContext (unitName = "bicitoursPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param comentarioEntity objeto comentario que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ComentarioEntity create(ComentarioEntity comentarioEntity) {
        LOGGER.log(Level.INFO, "Creando un comentario nuevo");
        em.persist(comentarioEntity);
        LOGGER.log(Level.INFO, "Comentario creado");
        return comentarioEntity;
    }
    
    public ComentarioEntity find (long comentarioId)
    {
        return em.find(ComentarioEntity.class, comentarioId);
    }
    
    public List<ComentarioEntity> findAll ()
    {
        Query query = em.createQuery("select u from ComentarioEntity u");
        return query.getResultList();
    }
    
    public ComentarioEntity update (ComentarioEntity comentarioEntity)
    {
        return em.merge(comentarioEntity);
    }
    
    public void delete (Long comentarioId)
    {
        ComentarioEntity comentarioEntity = find(comentarioId);
        em.remove(comentarioEntity);
    }
}
