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
public class AnteriorBlogLogic {
    @Inject
    private BlogPersistence blogPersistence;
    public BlogEntity getAnterior(Long blogsId) {

        return blogPersistence.find(blogsId).getAnterior();
    }
    
    public BlogEntity replaceAnterior(Long blogsId, Long blogsAnteriorId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        blogEntity.setAnterior(blogPersistence.find(blogsAnteriorId));
        return blogPersistence.find(blogsAnteriorId);
        
    }
    public void removeAnterior(Long blogsId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        blogEntity.setAnterior(null);
    }
}
