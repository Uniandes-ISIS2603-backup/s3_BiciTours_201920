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
@Path("books")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class BlogResource {

    @Inject
    private BlogLogic blogLogic;
    @POST
    public BlogDTO createBlog(BlogDTO blog) throws BusinessLogicException {
        BlogDTO nuevoBlogDTO = new BlogDTO(blogLogic.createBlog(blog.toEntity()));
        return nuevoBlogDTO;
    }
    @GET
    public List<BlogDetailDTO> getBlogs() {
        List<BlogDetailDTO> listaBlogs = listEntity2DetailDTO(blogLogic.getBlogs());
        return listaBlogs;
    }

    @PUT
    @Path("{blogsId: \\d+}")
    public BlogDetailDTO updateBlog(@PathParam("blogsId") Long blogsId, BlogDetailDTO blog) throws BusinessLogicException {
        blog.setId(blogsId);
        if (blogLogic.getBlog(blogsId) == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + " no existe.", 404);
        }
        BlogDetailDTO detailDTO = new BlogDetailDTO(blogLogic.updateBlog(blog.toEntity()));
        return detailDTO;
    }

    @DELETE
    @Path("{blogsId: \\d+}")
    public void deleteBlog(@PathParam("blogsId") Long blogsId) throws BusinessLogicException {
        BlogEntity entity = blogLogic.getBlog(blogsId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /blogs/" + blogsId + " no existe.", 404);
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
