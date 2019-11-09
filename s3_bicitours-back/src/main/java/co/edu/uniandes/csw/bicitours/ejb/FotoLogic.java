/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.FotoPersistence;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Jhuliana Barrios
 */
@Stateless
public class FotoLogic {

    @Inject
    private FotoPersistence fp;
    
    @Inject
    private TourPersistence tp;
   
    /**
     * Se encarga de crear una foto en la base de datos.
     *
     * @param foto Objeto de FotoEntity con los datos nuevos
     * @param tourId id del tour el cual contendrá la foto.
     * @return Objeto de FotoEntity con los datos nuevos y su ID.
     *
     */
    public FotoEntity createFoto( Long tourId,FotoEntity foto)
    {
        TourEntity  t = tp.find(tourId);
        foto.setTour(t);
        foto = fp.create(foto);
        return foto;
    }

    /**
     * Devuelve todas las fotospertenecientes a un tour
     * @param tourId, id del tour que contiene la foto
     * @return una lista con todas las fotos del tour
     */
    public List<FotoEntity> getFotos(Long tourId) {
        return tp.find(tourId).getFotos();
    }

    /**
     * Busca un foto en la base de datos perteneciente a un tour
     * @param id, id de la foto
     * @param tourId, id del tour que contiene la foto
     * @return una entidad foto con el id dado por parámetro, null en caso de que no exista en el tour dado
     */
    public FotoEntity getFoto(Long tourId, Long id)
    {
       return fp.find(tourId, id);
    }

    /**
     * Actualiza una foto que existe en la base de datos
     * @param tourId, id del tour al que pertenece la foto
     * @param foto la entidad foto que se quiere actualizar
     * @return la entidad foto actualizada
     */
    public FotoEntity updateFoto(Long tourId, FotoEntity foto)
    { 
        TourEntity tourEntity = tp.find(tourId);
        foto.setTour(tourEntity);
        fp.update(foto);
        return foto;
    }

    /**
     * Elimina la foto con la identificación dada por parámetro
     * @param tourId, id del tour al que pertenece la foto
     * @param id identificación de la foto
     * @throws co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException cuando en el tour no existe dicha foto
     */
    public void deleteFoto(Long tourId, Long id) throws BusinessLogicException
    {
        fp.delete(tourId,id);
    }    
}
