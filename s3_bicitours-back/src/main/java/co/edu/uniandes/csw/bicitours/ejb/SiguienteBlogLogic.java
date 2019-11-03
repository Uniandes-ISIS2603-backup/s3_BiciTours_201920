/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class SiguienteBlogLogic {

    @Inject
    private BlogPersistence blogPersistence;

    public BlogEntity getSiguiente(Long blogsId) {

        return blogPersistence.find(blogsId).getSiguiente();
    }

    public BlogEntity replaceSiguiente(Long blogsId, Long blogsSiguienteId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        blogEntity.setSiguiente(blogPersistence.find(blogsSiguienteId));
        return blogPersistence.find(blogsSiguienteId);

    }

    public void removeSiguiente(Long blogsId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        blogEntity.setSiguiente(null);
    }

}
