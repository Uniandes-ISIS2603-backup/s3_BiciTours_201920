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
public class FavoritosUsuariosLogic {
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
        if(blogs.contains(blogEntity))
        {
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
        public void removeFavorito(Long usuariosId, Long blogsId) throws BusinessLogicException{
        blogPersistence.delete(getFavorito(usuariosId,blogsId).getId());
    }
        public UsuarioEntity addUsuario(Long usuariosId, Long blogsId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        usuarioEntity.getFavoritos().add(blogEntity);
        return usuarioEntity;
    }

    public List<UsuarioEntity> getUsuarios(Long blogsId) {
        return blogPersistence.find(blogsId).getUsuarios();
    }

    public UsuarioEntity getUsuario(Long blogsId, Long usuariosId) throws BusinessLogicException {
        List<UsuarioEntity> usuarios = blogPersistence.find(blogsId).getUsuarios();
        UsuarioEntity usuarioEntity = usuarioPersistence.find(usuariosId);
        if(usuarios.contains(usuarioEntity))
        {
            return usuarioEntity;
        }
        throw new BusinessLogicException("El usuario no está asociado al blog");
    }

    public List<UsuarioEntity> replaceUsuarios(Long blogsId, List<UsuarioEntity> usuarios) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        blogEntity.setUsuarios(new ArrayList<UsuarioEntity>());
        List<UsuarioEntity> usuariosList = usuarioPersistence.findAll();
        for (UsuarioEntity usuario : usuariosList) {
            if (usuarios.contains(usuario)) {
                usuario.getFavoritos().add(blogEntity);
            } else if (!usuario.getFavoritos().isEmpty() && usuario.getFavoritos().contains(blogEntity)) {
                usuario.getFavoritos().remove(blogEntity);
        }
        }
        return usuarios;
    }
        public void removeUsuario(Long blogsId, Long usuariosId) throws BusinessLogicException{
        usuarioPersistence.delete(getUsuario(usuariosId,blogsId).getId());
    }
}
