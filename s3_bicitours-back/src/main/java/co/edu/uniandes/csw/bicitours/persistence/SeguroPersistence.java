/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;

import java.util.List;

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

    @PersistenceContext(unitName = "bicitoursPU")
    protected EntityManager e;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param SeguroEntity objeto libro que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SeguroEntity create(SeguroEntity seguroentity) {

        e.persist(seguroentity);

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

        Query q = e.createQuery("select u from SeguroEntity u");
        return q.getResultList();
    }

    /**
     * Busca si hay algun lubro con el id que se envía de argumento
     *
     * @param segurosId: id correspondiente al libro buscado.
     * @return un libro.
     */
    public SeguroEntity find(Long segurosId) {

        return e.find(SeguroEntity.class, segurosId);
    }

    /**
     * Actualiza un libro.
     *
     * @param seguroEntity: el libro que viene con los nuevos cambios. Por
     * ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un libro con los cambios aplicados.
     */
    public SeguroEntity update(SeguroEntity seguroEntity) {

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

        SeguroEntity seguroEntity = e.find(SeguroEntity.class, segurosId);
        e.remove(seguroEntity);
    }

}
