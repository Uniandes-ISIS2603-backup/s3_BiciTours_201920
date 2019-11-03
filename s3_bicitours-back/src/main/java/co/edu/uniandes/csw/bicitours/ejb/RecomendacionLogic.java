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

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Maria Clara Noguera Echeverri
 */
@Stateless
public class RecomendacionLogic {


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

        RecomendacionEntity newRecomendacionEntity = persistence.create(recomendacionEntity);

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



        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Recomendacion a partir de su ID.
     *
     * @param recomendacionsId Identificador de la instancia a consultar
     * @return Instancia de RecomendacionEntity con los datos del Recomendacion consultado.
     */
    public RecomendacionEntity getRecomendacion(Long recomendacionesId) {

        RecomendacionEntity recomendacionEntity = persistence.find(recomendacionesId);
        if (recomendacionEntity == null) {

        }

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



        return persistence.update(recomendacionEntity);
    }
     public RecomendacionEntity updateRecomendacion(RecomendacionEntity recomendacionEntity) {

        return persistence.update(recomendacionEntity);
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