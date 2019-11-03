/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class UsuarioMisBlogsLogic {

    @Inject
    private BlogPersistence blogPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    public BlogEntity addBlog(Long blogsId, Long usuariosId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        blogEntity.setCreador(usuarioEntity);
        return blogEntity;
    }

    public List<BlogEntity> getBlogs(Long usuariosId) {
        return usuarioPersistence.find(usuariosId).getMisBlogs();
    }

    public BlogEntity getBlog(Long usuariosId, Long blogsId) throws BusinessLogicException {
        List<BlogEntity> blogs = usuarioPersistence.find(usuariosId).getMisBlogs();
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        if (blogs.contains(blogEntity)) {
            return blogEntity;
        }
        throw new BusinessLogicException("El blog no está asociado a el usuario");
    }

    public List<BlogEntity> replaceBlogs(Long usuariosId, List<BlogEntity> blogs) {
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        List<BlogEntity> blogsList = blogPersistence.findAll();
        for (BlogEntity blog : blogsList) {
            if (blogs.contains(blog)) {
                blog.setCreador(usuarioEntity);
            } else if (blog.getCreador() != null && blog.getCreador().equals(usuarioEntity)) {
                blog.setCreador(null);
            }
        }
        return blogs;
    }

    public void removeBlog(Long usuariosId, Long blogsId) throws BusinessLogicException {
        blogPersistence.delete(getBlog(usuariosId, blogsId).getId());
    }
}
