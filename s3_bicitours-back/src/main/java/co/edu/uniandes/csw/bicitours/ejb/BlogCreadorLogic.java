/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class BlogCreadorLogic {

    @Inject
    private BlogPersistence blogPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;

    public UsuarioEntity getCreador(Long blogsId) {

        return blogPersistence.find(blogsId).getCreador();
    }

    public UsuarioEntity replaceCreador(Long blogsId, Long usuariosId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        blogEntity.setCreador(usuarioPersistence.find(usuariosId));
        return usuarioPersistence.find(usuariosId);

    }

    public void removeCreador(Long blogsId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        UsuarioEntity usuarioEntity = usuarioPersistence.find(blogEntity.getCreador().getId());
        usuarioEntity.getMisBlogs().remove(blogEntity);
        blogEntity.setCreador(null);
    }
}
