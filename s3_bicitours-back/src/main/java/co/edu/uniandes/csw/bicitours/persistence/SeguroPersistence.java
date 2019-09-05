/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
import co.edu.uniandes.csw.bicitours.persistence.SeguroPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Maria Clara Noguera Echeverri
 */
@Stateless
public class SeguroPersistence {
    
     private static final Logger LOGGER = Logger.getLogger(SeguroPersistence.class.getName());

    @PersistenceContext(unitName = "SeguroStorePU")
    protected EntityManager e;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param SeguroEntity objeto libro que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SeguroEntity create(SeguroEntity seguroentity) {
        LOGGER.log(Level.INFO, "Creando un libro nuevo");
        e.persist(seguroentity);
        LOGGER.log(Level.INFO, "Libro creado");
        return seguroentity;
    }

    /**
     * Devuelve todos loslibros de la base de datos.
     *
     * @return una lista con todos los libros que encuentre en la base de datos,
     * "select u from SeguroEntity u" es como un "select * from SeguroEntity;" -
     * "SELECT * FROM table_name" en SQL.
     */
    public List<SeguroEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los libros");
        Query q = e.createQuery("select u from SeguroEntity u");
        return q.getResultList();
    }
   @PersistenceContext(unitName = "bicitoursPU")  
    protected EntityManager em;
    
    

    /**
     * Busca si hay algun lubro con el id que se envía de argumento
     *
     * @param segurosId: id correspondiente al libro buscado.
     * @return un libro.
     */
    public SeguroEntity find(Long segurosId) {
        LOGGER.log(Level.INFO, "Consultando el libro con id={0}", segurosId);
        return e.find(SeguroEntity.class, segurosId);
    }

    /**
     * Actualiza un libro.
     *
     * @param seguroEntity: el libro que viene con los nuevos cambios. Por ejemplo
     * el nombre pudo cambiar. En ese caso, se haria uso del método update.
     * @return un libro con los cambios aplicados.
     */
    public SeguroEntity update(SeguroEntity seguroEntity) {
        LOGGER.log(Level.INFO, "Actualizando el libro con id={0}", seguroEntity.getId());
        return e.merge(seguroEntity);
    }

    /**
     *
     * Borra un libro de la base de datos recibiendo como argumento el id del
     * libro
     *
     * @param segurosId: id correspondiente al libro a borrar.
     */
    public void delete(Long segurosId) {
        LOGGER.log(Level.INFO, "Borrando el libro con id={0}", segurosId);
        SeguroEntity seguroEntity = e.find(SeguroEntity.class, segurosId);
        e.remove(seguroEntity);
    }

    /**
     * Busca si hay algun libro con el ISBN que se envía de argumento
     *
     * @param id: ISBN de la editorial que se está buscando
     * @return null si no existe ningun libro con el id del argumento. Si
     * existe alguno devuelve el primero.
     */
    public SeguroEntity findById(String id) {
        LOGGER.log(Level.INFO, "Consultando libros por id ", id);
        // Se crea un query para buscar libros con el id que recibe el método como argumento. ":id" es un placeholder que debe ser remplazado
        TypedQuery query = e.createQuery("Select e From SeguroEntity e where e.id = :id", SeguroEntity.class);
        // Se remplaza el placeholder ":id" con el valor del argumento 
        query = query.setParameter("id", id);
        // Se invoca el query se obtiene la lista resultado
        List<SeguroEntity> sameISBN = query.getResultList();
        SeguroEntity result;
        if (sameISBN == null) {
            result = null;
        } else if (sameISBN.isEmpty()) {
            result = null;
        } else {
            result = sameISBN.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar libros por id ", id);
        return result;
    }
    
}
