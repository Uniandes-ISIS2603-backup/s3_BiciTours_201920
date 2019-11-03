/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class TourBlogsLogic {

    @Inject
    private BlogPersistence blogPersistence;

    @Inject
    private TourPersistence tourPersistence;

    public BlogEntity addBlog(Long blogsId, Long toursId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        TourEntity tourEntity = tourPersistence.find(toursId);
        blogEntity.setTour(tourEntity);
        return blogEntity;
    }

    public List<BlogEntity> getBlogs(Long toursId) {
        return tourPersistence.find(toursId).getBlogs();
    }

    public BlogEntity getBlog(Long toursId, Long blogsId) throws BusinessLogicException {
        List<BlogEntity> blogs = tourPersistence.find(toursId).getBlogs();
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        if (blogs.contains(blogEntity)) {
            return blogEntity;
        }
        throw new BusinessLogicException("El blog no está asociado a el tour");
    }

    public List<BlogEntity> replaceBlogs(Long toursId, List<BlogEntity> blogs) {
        TourEntity tourEntity = tourPersistence.find(toursId);
        List<BlogEntity> blogsList = blogPersistence.findAll();
        for (BlogEntity blog : blogsList) {
            if (blogs.contains(blog)) {
                blog.setTour(tourEntity);
            } else if (blog.getTour() != null && blog.getTour().equals(tourEntity)) {
                blog.setTour(null);
            }
        }
        return blogs;
    }

    public void removeBlog(Long ToursId, Long blogsId) throws BusinessLogicException {
        blogPersistence.delete(getBlog(ToursId, blogsId).getId());
    }
}
