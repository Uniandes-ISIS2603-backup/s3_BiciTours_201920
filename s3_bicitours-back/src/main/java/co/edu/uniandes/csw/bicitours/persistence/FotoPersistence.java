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
     * Busca si existe una foto con el id pasado por parámetro
     *
     * @param fotoId: id correspondiente a la foto buscada.
     * @return la foto buscada.
     */
    public FotoEntity find(long fotoId) {
        return em.find(FotoEntity.class, fotoId);
    }

    /**
     * Devuelve una lista con todas las fotos de la base de datos.
     *
     * @return una lista con todas las fotos que encuentre en la base de datos,
     */
    public List<FotoEntity> findAll() {

        TypedQuery<FotoEntity> query = em.createQuery("select u from FotoEntity u", FotoEntity.class);
        return query.getResultList();
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

    public void delete(Long fotoId) {
        FotoEntity fotoEntity = find(fotoId);
        em.remove(fotoEntity);
    }

}
