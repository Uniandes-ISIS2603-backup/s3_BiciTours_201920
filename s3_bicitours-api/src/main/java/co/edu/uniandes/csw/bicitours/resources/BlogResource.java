/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.BlogDTO;
import co.edu.uniandes.csw.bicitours.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Path("blogs")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class BlogResource {
    private static final String RECURSO="El recurso /blogs/";
    private static final String NOEXISTE=" no existe.";
    @Inject
    private BlogLogic blogLogic;
    @POST
    public BlogDTO createBlog(BlogDTO blog) throws BusinessLogicException {

        return new BlogDTO(blogLogic.createBlog(blog.toEntity()));
    }
    @GET
    public List<BlogDetailDTO> getBlogs() {
 
        return listEntity2DetailDTO(blogLogic.getBlogs());
    }
    @GET
    @Path("{blogsId: \\d+}")
    public BlogDetailDTO getBlog(@PathParam("blogsId") Long blogsId) {
        BlogEntity blogEntity = blogLogic.getBlog(blogsId);
        if (blogEntity == null) {
            throw new WebApplicationException(RECURSO + blogsId + NOEXISTE, 404);
        }

        return new BlogDetailDTO(blogEntity);
    }
    @PUT
    @Path("{blogsId: \\d+}")
    public BlogDetailDTO updateBlog(@PathParam("blogsId") Long blogsId, BlogDetailDTO blog) throws BusinessLogicException {
        blog.setId(blogsId);
        if (blogLogic.getBlog(blogsId) == null) {
            throw new WebApplicationException(RECURSO + blogsId + NOEXISTE, 404);
        }

        return new BlogDetailDTO(blogLogic.updateBlog(blog.toEntity()));
    }

    @DELETE
    @Path("{blogsId: \\d+}")
    public void deleteBlog(@PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        BlogEntity entity = blogLogic.getBlog(blogsId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO + blogsId + NOEXISTE, 404);
        }
        blogLogic.deleteBlog(blogsId);
    }

    private List<BlogDetailDTO> listEntity2DetailDTO(List<BlogEntity> entityList) {
        List<BlogDetailDTO> list = new ArrayList<>();
        for (BlogEntity entity : entityList) {
            list.add(new BlogDetailDTO(entity));
        }
        return list;
    }
}
