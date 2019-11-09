/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    private static final Logger LOGGER = Logger.getLogger(FotoPersistence.class.getName());
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param fotoEntity foto que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public FotoEntity create(FotoEntity fotoEntity) {
        LOGGER.log(Level.INFO, "Creando una foto nueva");
        em.persist(fotoEntity);
        LOGGER.log(Level.INFO, "foto creada");
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
        System.out.println("id del parámetro: "+ toursId + "  id del tour: "+t);
        if(t == toursId)
            return e;
        else
            return null;
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
        LOGGER.log(Level.INFO, "Actualizando la foto con id={0}", fotoEntity.getId());
        return em.merge(fotoEntity);
    }
        
}

