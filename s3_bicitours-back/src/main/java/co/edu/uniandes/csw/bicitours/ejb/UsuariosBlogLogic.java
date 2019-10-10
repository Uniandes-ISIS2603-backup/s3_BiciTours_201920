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
public class UsuariosBlogLogic {
    @Inject
    private BlogPersistence blogPersistence;

    @Inject
    private UsuarioPersistence usuarioPersistence;
    
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

        public void removeUsuario(Long blogsId, Long usuariosId) throws BusinessLogicException{
        usuarioPersistence.delete(getUsuario(usuariosId,blogsId).getId());
    }    
}
