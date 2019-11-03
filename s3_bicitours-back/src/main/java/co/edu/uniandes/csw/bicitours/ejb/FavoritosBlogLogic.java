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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class FavoritosBlogLogic {

    @Inject
    private BlogPersistence blogPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    public BlogEntity addFavorito(Long blogsId, Long usuariosId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        blogEntity.getUsuarios().add(usuarioEntity);
        return blogEntity;
    }

    public List<BlogEntity> getFavoritos(Long usuariosId) {
        return usuarioPersistence.find(usuariosId).getFavoritos();
    }

    public BlogEntity getFavorito(Long usuariosId, Long blogsId) throws BusinessLogicException {
        List<BlogEntity> blogs = usuarioPersistence.find(usuariosId).getFavoritos();
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        if (blogs.contains(blogEntity)) {
            return blogEntity;
        }
        throw new BusinessLogicException("El blog no está asociado a el usuario");
    }

    public List<BlogEntity> replaceFavoritos(Long usuariosId, List<BlogEntity> blogs) {
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        usuarioEntity.setFavoritos(new ArrayList<BlogEntity>());
        List<BlogEntity> blogsList = blogPersistence.findAll();
        for (BlogEntity blog : blogsList) {
            if (blogs.contains(blog)) {
                blog.getUsuarios().add(usuarioEntity);
            } else if (!blog.getUsuarios().isEmpty() && blog.getUsuarios().contains(usuarioEntity)) {
                blog.getUsuarios().remove(usuarioEntity);
            }
        }
        return blogs;
    }

    public void removeFavorito(Long usuariosId, Long blogsId) throws BusinessLogicException {
        blogPersistence.delete(getFavorito(usuariosId, blogsId).getId());
    }

}
