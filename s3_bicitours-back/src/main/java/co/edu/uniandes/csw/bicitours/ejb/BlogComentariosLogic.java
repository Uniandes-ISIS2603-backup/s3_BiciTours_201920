/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import co.edu.uniandes.csw.bicitours.persistence.ComentarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class BlogComentariosLogic {

    @Inject
    private BlogPersistence blogPersistence;

    @Inject
    private ComentarioPersistence comentarioPersistence;

    public ComentarioEntity addComentario(Long comentariosId, Long blogsId) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        ComentarioEntity comentarioEntity = comentarioPersistence.find(comentariosId);
        comentarioEntity.setBlog(blogEntity);
        return comentarioEntity;
    }

    public List<ComentarioEntity> getComentarios(Long blogsId) {
        return blogPersistence.find(blogsId).getComentarios();
    }

    public ComentarioEntity getComentario(Long comentariosId, Long blogsId) throws BusinessLogicException {
        List<ComentarioEntity> comentarios = blogPersistence.find(blogsId).getComentarios();
        ComentarioEntity comentarioEntity = comentarioPersistence.find(comentariosId);
        if (comentarios.contains(comentarioEntity)) {
            return comentarioEntity;
        }
        throw new BusinessLogicException("El comentario no está asociado a el blog");
    }

    public List<ComentarioEntity> replaceComentarios(Long blogsId, List<ComentarioEntity> comentarios) {
        BlogEntity blogEntity = blogPersistence.find(blogsId);
        List<ComentarioEntity> comentariosList = comentarioPersistence.findAll();
        for (ComentarioEntity comentario : comentariosList) {
            if (comentarios.contains(comentario)) {
                comentario.setBlog(blogEntity);
            } else if (comentario.getBlog() != null && comentario.getBlog().equals(blogEntity)) {
                comentario.setBlog(null);
            }
        }
        return comentarios;
    }

    public void removeComentario(Long comentariosId, Long blogsId) throws BusinessLogicException {
        comentarioPersistence.delete(getComentario(comentariosId, blogsId).getId());
    }
}
