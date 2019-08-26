/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
*@author Jhuliana Barrios 
*/

@Stateless
public class TourPersistence{
    @PersistenceContext(unitName = "bicitoursPU")

    protected EntityManager em;
    
    private static final Logger LOGGER = Logger.getLogger(TourPersistence.class.getName());

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param tourEntity objeto tour que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public TourEntity create(TourEntity tourEntity) {
        LOGGER.log(Level.INFO, "Creando un Tour nuevo");
        em.persist(tourEntity);
        LOGGER.log(Level.INFO, "Tour creado");
        return tourEntity;
    }
    
    /**
     * Busca si existe un tour con el id pasado por parámetro
     * @param tourId: id correspondiente al tour buscado.
     * @return un tour.
     */
    public TourEntity find(long tourId)
    {
 //       LOGGER.log(Level.INFO, "Consultando el blog con id={0}", tourId);
        return em.find(TourEntity.class, tourId);
    }
    
         /**
     * Devuelve una lista con todos los toures de la base de datos.
     * @return una lista con todos los toures que encuentre en la base de datos,
     */
    public List<TourEntity> findAll()
    {
        LOGGER.log(Level.INFO, "Consultando todos los toures");
        TypedQuery<TourEntity> query=em.createQuery("select u from TourEntity u", TourEntity.class);
        return query.getResultList();
    }
    
    /**
     * Actualiza un tour.
     *
     * @param tourEntity
     * @return un tour con los cambios aplicados.
     */
    public TourEntity update(TourEntity tourEntity) {
        LOGGER.log(Level.INFO, "Actualizando el tour con id={0}", tourEntity.getId());
        return em.merge(tourEntity);
    }

        public void delete(Long tourId)
    {
        TourEntity tourEntity = find(tourId);
        em.remove(tourEntity);
    }
	
}
