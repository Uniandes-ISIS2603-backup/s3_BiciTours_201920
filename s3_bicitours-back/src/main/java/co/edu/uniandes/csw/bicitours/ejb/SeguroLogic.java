/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.SeguroPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @seguro Maria Clara Noguera Echeverri
 */
@Stateless
public class SeguroLogic {
      private static final Logger LOGGER = Logger.getLogger(SeguroLogic.class.getName());

    @Inject
    private SeguroPersistence persistence;

    /**
     * Se encarga de crear un Seguro en la base de datos.
     *
     * @param seguroEntity Objeto de SeguroEntity con los datos nuevos
     * @return Objeto de SeguroEntity con los datos nuevos y su ID.
     */
    public SeguroEntity createSeguro(SeguroEntity seguroEntity) throws BusinessLogicException {
        if(seguroEntity.getCaracteristicas()!=null && seguroEntity.getCondiciones()!=null && seguroEntity.getTipo()!=null)
        {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del seguro");
        SeguroEntity newSeguroEntity = persistence.create(seguroEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del seguro");
        return newSeguroEntity;
        }
        else
              {
            throw new BusinessLogicException("Los campos ingresados para la informacion del seguro no son correctos"); 
        }
    }

    /**
     * Obtiene la lista de los registros de Seguro.
     *
     * @return Colección de objetos de SeguroEntity.
     */
    public List<SeguroEntity> getSeguros() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los seguros");
        List<SeguroEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los seguros");
        return lista;
    }

    /**
     * Obtiene los datos de una instancia de Seguro a partir de su ID.
     *
     * @param segurosId Identificador de la instancia a consultar
     * @return Instancia de SeguroEntity con los datos del Seguro consultado.
     */
    public SeguroEntity getSeguro(Long segurosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el seguro con id = {0}", segurosId);
        SeguroEntity seguroEntity = persistence.find(segurosId);
        if (seguroEntity == null) {
            LOGGER.log(Level.SEVERE, "La editorial con el id = {0} no existe", segurosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el seguro con id = {0}", segurosId);
        return seguroEntity;
    }

    /**
     * Actualiza la información de una instancia de Seguro.
     *
     * @param segurosId Identificador de la instancia a actualizar
     * @param seguroEntity Instancia de SeguroEntity con los nuevos datos.
     * @return Instancia de SeguroEntity con los datos actualizados.
     */
    public SeguroEntity updateSeguro(Long segurosId, SeguroEntity seguroEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el seguro con id = {0}", segurosId);
        SeguroEntity newSeguroEntity = persistence.update(seguroEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el seguro con id = {0}", segurosId);
        return newSeguroEntity;
    }
     public SeguroEntity updateSeguro(SeguroEntity seguroEntity) {
        SeguroEntity newEntity = persistence.update(seguroEntity);
        return newEntity;
    }

    /**
     * Elimina una instancia de Seguro de la base de datos.
     *
     * @param segurosId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si el seguro tiene libros asociados.
     */
    public void deleteSeguro(Long segurosId) {
        persistence.delete(segurosId);
    }
}
