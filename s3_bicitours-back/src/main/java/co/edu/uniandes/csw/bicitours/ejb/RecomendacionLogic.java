/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.RecomendacionEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.RecomendacionPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Maria Clara Noguera Echeverri
 */
@Stateless
public class RecomendacionLogic {
      private static final Logger LOGGER = Logger.getLogger(RecomendacionLogic.class.getName());

    @Inject
    private RecomendacionPersistence persistence;

    /**
     * Se encarga de crear un Recomendacion en la base de datos.
     *
     * @param recomendacionEntity Objeto de RecomendacionEntity con los datos nuevos
     * @return Objeto de RecomendacionEntity con los datos nuevos y su ID.
     */
    public RecomendacionEntity createRecomendacion(RecomendacionEntity recomendacionEntity) throws BusinessLogicException {
        if(recomendacionEntity.getIndumentaria()!=null && recomendacionEntity.getTipoBici()!=null)
        {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la recomendacion");
        RecomendacionEntity newRecomendacionEntity = persistence.create(recomendacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la recomendacion");
        return newRecomendacionEntity;
        }
        else
              {
            throw new BusinessLogicException("Los campos ingresados para la informacion de la recomendacion no son correctos"); 
        }
    }

    /**
     * Obtiene la lista de los registros de Recomendacion.
     *
     * @return Colección de objetos de RecomendacionEntity.
     */
    public List<RecomendacionEntity> getRecomendacions() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las recomendaciones");
        List<RecomendacionEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las  recomendaciones");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Recomendacion a partir de su ID.
     *
     * @param recomendacionsId Identificador de la instancia a consultar
     * @return Instancia de RecomendacionEntity con los datos del Recomendacion consultado.
     */
    public RecomendacionEntity getRecomendacion(Long recomendacionesId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el recomendacion con id = {0}", recomendacionesId);
        RecomendacionEntity recomendacionEntity = persistence.find(recomendacionesId);
        if (recomendacionEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", recomendacionesId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el recomendacion con id = {0}", recomendacionesId);
        return recomendacionEntity;
    }

    /**
     * Actualiza la información de una instancia de Recomendacion.
     *
     * @param recomendacionsId Identificador de la instancia a actualizar
     * @param recomendacionEntity Instancia de RecomendacionEntity con los nuevos datos.
     * @return Instancia de RecomendacionEntity con los datos actualizados.
     */
    public RecomendacionEntity updateRecomendacion(Long recomendacionsId, RecomendacionEntity recomendacionEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el recomendacion con id = {0}", recomendacionsId);
        RecomendacionEntity newRecomendacionEntity = persistence.update(recomendacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el recomendacion con id = {0}", recomendacionsId);
        return newRecomendacionEntity;
    }
     public RecomendacionEntity updateRecomendacion(RecomendacionEntity recomendacionEntity) {
        RecomendacionEntity newEntity = persistence.update(recomendacionEntity);
        return newEntity;
    }

    /**
     * Elimina una instancia de Recomendacion de la base de datos.
     *
     * @param recomendacionsId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el recomendacion tiene libros asociados.
     */
    public void deleteRecomendacion(Long recomendacionsId) {
        persistence.delete(recomendacionsId);
} 
}