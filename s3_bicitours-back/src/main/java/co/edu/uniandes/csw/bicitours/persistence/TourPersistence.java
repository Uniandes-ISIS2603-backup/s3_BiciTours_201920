/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import java.util.List;

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
    


    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param tourEntity objeto tour que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public TourEntity create(TourEntity tourEntity) {

        em.persist(tourEntity);

        return tourEntity;
    }
    
    /**
     * Busca si existe un tour con el id pasado por parámetro
     * @param tourId: id correspondiente al tour buscado.
     * @return un tour.
     */
    public TourEntity find(long tourId)
    {

        return em.find(TourEntity.class, tourId);
    }
    
         /**
     * Devuelve una lista con todos los toures de la base de datos.
     * @return una lista con todos los toures que encuentre en la base de datos,
     */
    public List<TourEntity> findAll()
    {

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

        return em.merge(tourEntity);
    }

        public void delete(Long tourId)
    {
        TourEntity tourEntity = find(tourId);
        em.remove(tourEntity);
    }
	
}
