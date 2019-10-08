/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
import co.edu.uniandes.csw.bicitours.persistence.ComentarioPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 *  @author JuanRueda
 */

@Stateless
public class ComentarioUsuarioLogic {
    @Inject
    private ComentarioPersistence persistenciaC;

    @Inject
    private UsuarioPersistence persistenciaU;

    /**
     * Asocia un usuario existente a un comentario
     *
     * @param comentarioId Identificador de la instancia de comentario
     * @param usuarioId Identificador de la instancia de usuario
     * @return Instancia de UsuarioEntity que fue asociada a comentario
     */
    //public UsuarioEntity setUsuario(Long comentarioId, Long usuarioId) {
        //UsuarioEntity entidadU = persistenciaU.find(usuarioId);
        //ComentarioEntity entidadC = persistenciaC.find(comentarioId);
        //entidadC.setUsuario(entidadU);
        //return persistenciaU.find(usuarioId);
    //}

    /**
     * Obtiene una instancia de UsuarioEntity asociada a una instancia de comentario
     *
     * @param comentarioId Identificador de la instancia de comentario
     * @return La entidad del Autor asociada al libro
     */
    //public UsuarioEntity getUsuario(Long comentarioId) {
    //    UsuarioEntity usuario = persistenciaC.find(comentarioId).getUsuario();
    //    return usuario;
    //}

    /**
     * Desasocia un usuario existente de un comentario existente
     *
     * @param comentarioId Identificador de la instancia de comentario
     */
    //public void removeUsuario(Long comentarioId) {
    //    ComentarioEntity entidadC = persistenciaC.find(comentarioId);
    //    entidadC.setUsuario(null);
    //}
}
