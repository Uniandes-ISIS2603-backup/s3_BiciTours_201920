/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import javax.inject.Inject;

/**
 *
 * @author JuanRueda
 */
public class ComentarioBlogLogic {
    @Inject
    private ComentarioPersistence persistenciaC;

    @Inject
    private BlogPersistence persistenciaB;

    /**
     * Asocia un blog existente a un comentario
     *
     * @param comentarioId Identificador de la instancia de comentario
     * @param blogId Identificador de la instancia de blog
     * @return Instancia de BlogEntity que fue asociada a comentario
     */
    public BlogEntity setBlog(Long comentarioId, Long blogId) {
        BlogEntity entidadU = persistenciaB.find(blogId);
        ComentarioEntity entidadC = persistenciaC.find(comentarioId);
        entidadC.setBlog(entidadU);
        return persistenciaB.find(blogId);
    }

    /**
     * Obtiene una instancia de BlogEntity asociada a una instancia de comentario
     *
     * @param comentarioId Identificador de la instancia de comentario
     * @return La entidad del Autor asociada al libro
     */
    public BlogEntity getBlog(Long comentarioId) {
        BlogEntity blog = persistenciaC.find(comentarioId).getBlog();
        return blog;
    }

    /**
     * Desasocia un blog existente de un comentario existente
     *
     * @param comentarioId Identificador de la instancia de comentario
     */
    public void removeBlog(Long comentarioId) {
        ComentarioEntity entidadC = persistenciaC.find(comentarioId);
        entidadC.setBlog(null);
    }
}
