/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class BlogTourLogic {

    @Inject
    private BlogPersistence blogPersistence;

    @Inject
    private TourPersistence tourPersistence;

    public TourEntity getTour(Long blogsId) {

        return blogPersistence.find(blogsId).getTour();
    }

    public BlogEntity replaceTour(Long blogsId, Long toursId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        TourEntity tourEntity = tourPersistence.find(blogEntity.getTour().getId());
        tourEntity.getBlogs().remove(blogEntity);
        blogEntity.setTour(tourPersistence.find(toursId));
        return blogPersistence.find(blogsId);
    }

    public void removeTour(Long blogsId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        TourEntity tourEntity = tourPersistence.find(blogEntity.getTour().getId());
        tourEntity.getBlogs().remove(blogEntity);
        blogEntity.setTour(null);
    }
}
