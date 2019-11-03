/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.FotoEntity;

import co.edu.uniandes.csw.bicitours.persistence.FotoPersistence;
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
    
    public FotoEntity createFoto(FotoEntity foto)
    {
        foto = fp.create(foto);
        return foto;
    }
   /**
     * Devuelve todas las fotos creadas
     * @return una lista con todas las fotos creadas
     */
    public List<FotoEntity> getFotos() {
        return fp.findAll();
    }
    
    /**
     * Busca un foto en la base de datos con un id dado
     * @param id, id de la foto
     * @return una entidad foto con el id dado por parámetro, null en caso de que no exista
     */
    public FotoEntity getFoto(long id)
    {
        return fp.find(id);
    }
    
    /**
     * Actualiza una foto que existe en la base de datos
     * @param foto la entidad foto que se quiere actualizar
     * @return la entidad foto actualizada
     */
    public FotoEntity updateFoto(FotoEntity foto)
    {
        return fp.update(foto);
    }
    
    /**
     * Elimina la foto con la identificación dada por parámetro
     * @param id identificación de la foto
     */
    public void deleteFoto(long id)
    {
        fp.delete(id);
    }

    
}

