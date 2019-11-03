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

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Maria Clara Noguera Echeverri
 */
@Stateless
public class SeguroLogic {


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

        return persistence.create(seguroEntity);
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



        return persistence.findAll();
    }

    /**
     * Obtiene los datos de una instancia de Seguro a partir de su ID.
     *
     * @param segurosId Identificador de la instancia a consultar
     * @return Instancia de SeguroEntity con los datos del Seguro consultado.
     */
    public SeguroEntity getSeguro(Long segurosId) {



        return persistence.find(segurosId);
    }

    /**
     * Actualiza la información de una instancia de Seguro.
     *
     * @param segurosId Identificador de la instancia a actualizar
     * @param seguroEntity Instancia de SeguroEntity con los nuevos datos.
     * @return Instancia de SeguroEntity con los datos actualizados.
     */
    public SeguroEntity updateSeguro(Long segurosId, SeguroEntity seguroEntity) {



        return persistence.update(seguroEntity);
    }
     public SeguroEntity updateSeguro(SeguroEntity seguroEntity) {
 
        return persistence.update(seguroEntity);
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
