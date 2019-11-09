/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jhuliana Barrios
 */
@Stateless
public class FotoPersistence {

    @PersistenceContext(unitName = "bicitoursPU")

    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param fotoEntity foto que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FotoEntity create(FotoEntity fotoEntity) {

        em.persist(fotoEntity);

        return fotoEntity;
    }
    
    
    /**
     * Buscar una foto
     *
     * Busca si hay alguna foto asociada a un tour con un ID específico
     *
     * @param toursId El ID del tour con respecto al cual se busca
     * @param fotosId El ID de la foto buscada
     * @return La foto encontrada o null. Nota: Si existe una o más fotos
     * devuelve siempre la primera que encuentra
     */
    public FotoEntity find(Long toursId, Long fotosId) {
        FotoEntity e = em.find(FotoEntity.class, fotosId);
        Long t = e.getTour().getId();
        if(t == toursId)
            return e;
        else
            return null;
    }

    public List<FotoEntity> findAll() {

        TypedQuery<FotoEntity> query = em.createQuery("select u from FotoEntity u", FotoEntity.class);
        return query.getResultList();
    }

    /**
     * Borra una foto de la base de datos
     * @param fotoId , id de la foto a borrar
     * @param toursId, id del tour
     */
    public void delete(Long toursId,Long fotoId)
    {
        FotoEntity fEntity = find(toursId, fotoId);
        em.remove(fEntity);
    }
        
    /**
     * Actualiza una foto.
     *
     * @param fotoEntity
     * @return una foto con los cambios aplicados.
     */
    public FotoEntity update(FotoEntity fotoEntity) {

        return em.merge(fotoEntity);
    }

        
}


