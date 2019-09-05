/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Blog. Se conecta a través del Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class BlogPersistence {

    @PersistenceContext(unitName = "bicitoursPU")
    protected EntityManager em;

    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param blogEntity objeto blog que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public BlogEntity create(BlogEntity blogEntity) {
        em.persist(blogEntity);
        return blogEntity;
    }

    /**
     * Busca si hay algun blog con el id que se envía de argumento
     *
     * @param blogId: id correspondiente al blog buscado.
     * @return un blog.
     */
    public BlogEntity find(long blogId) {
        return em.find(BlogEntity.class, blogId);
    }

    /**
     * Devuelve todos los blogs de la base de datos.
     *
     * @return una lista con todos los blogs que encuentre en la base de datos,
     * "select u from blogEntity u" es como un "select * from BlogEntity;" -
     * "SELECT * FROM table_name" en SQL.
     */
    public List<BlogEntity> findAll() {
        TypedQuery<BlogEntity> query = em.createQuery("select u from BlogEntity u", BlogEntity.class);
        return query.getResultList();
    }

    /**
     * Actualiza un blog.
     *
     * @param blogEntity: el blog que viene con los nuevos cambios. Por ejemplo
     * el texto pudo cambiar. En ese caso, se haria uso del método update.
     * @return un blog con los cambios aplicados.
     */
    public BlogEntity update(BlogEntity blogEntity) {
        return em.merge(blogEntity);
    }

    /**
     *
     * Borra un blog de la base de datos recibiendo como argumento el id del
     * blog
     *
     * @param blogId: id correspondiente al blog a borrar.
     */
    public void delete(Long blogId) {
        BlogEntity blogEntity = em.find(BlogEntity.class, blogId);
        em.remove(blogEntity);
    }
}
