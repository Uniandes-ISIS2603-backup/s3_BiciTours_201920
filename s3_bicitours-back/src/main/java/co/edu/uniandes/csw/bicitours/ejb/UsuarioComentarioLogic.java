/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Sebastián González Rojas
 */
@Stateless
public class UsuarioComentarioLogic {
    //persistencias involucradas en creación de comentarios
    @Inject
    private UsuarioPersistence usuarioPersistence;
    @Inject
    private ComentarioPersistence comentarioPersistence;
    
    /**
     * @param comentarios the comentarios to set
     */
    public ComentarioEntity addComentario(Long comentariosId, Long usuarioId) {
        UsuarioEntity usuario = usuarioPersistence.find(usuarioId);
        ComentarioEntity comentario = comentarioPersistence.find(comentariosId);
        usuario.addComentario(comentario);
        return comentario;
    }
    /**
     * @param comentarios the comentarios to set
     */
    public void deleteComentario(ComentarioEntity comentario) {
        comentarioPersistence.delete(comentario.getId());
    }
    

    /**
     * Implementación del método getComentarios en lógica.
     * @param usuarioId Id de usuario a obtener comentarios
     * @return Lista de comentarios que pertenecen a usuario
     */
    public List<ComentarioEntity> getComentarios(Long usuarioId) {
        return usuarioPersistence.find(usuarioId).getComentarios(); //se obtiene directamente la lista desde la persistencia
    }

     /**
     * Retorna un comentario asociado a un autor usuario
     *
     * @param usuarioId El id del usuario a buscar.
     * @param comentarioId El id del comentario a buscar
     * @return El comentario encontrado dentro de la editorial.
     * @throws BusinessLogicException Si el comentario no se encuentra en el
     * usuario
     */
    public ComentarioEntity getComentario(Long usuarioId, Long comentarioId) throws BusinessLogicException {
        List<ComentarioEntity> comentariosUsuario = usuarioPersistence.find(usuarioId).getComentarios(); //primero se obtiene la lista de comentarios del usuario
        ComentarioEntity comentarioUsuario = comentarioPersistence.find(comentarioId);//se obtiene el comentario específico de la persistencia de comentarios
        int index = comentariosUsuario.indexOf(comentarioUsuario); //se obtiene el índice del comentario en la lista de comentarios del usuario   
        if (index >= 0) {
            return comentariosUsuario.get(index); //de existir este indice se retorna el objeto obtenido en el mismo
        }
        throw new BusinessLogicException("El comentario no está asociado al usuario");//de no estar asociado se envía un error de búsqueda
    }
    
    /**
     * Implementación del método setComentarios en lógica, no hay reglas de negocio en este caso
     * @param nuevosComentarios Lista de nuevos comentarios que serán asignados a usuario.
     * @param usuarioId El id de la editorial que se quiere actualizar.
     * @return La lista de comentarios actualizada.
     */
    public List<ComentarioEntity> setComentarios(Long usuarioId, List<ComentarioEntity> nuevosComentarios){
        UsuarioEntity usuario= usuarioPersistence.find(usuarioId); //se obtiene el usuario de la persistencia
        List<ComentarioEntity> comentarios = comentarioPersistence.findAll(); //se obtienen todos los comentarios de la persistencia
        for (ComentarioEntity comentarioActual : comentarios) { //se recorre la lista de comentarios para evaluar si ya existen comentarios
            if (nuevosComentarios.contains(comentarioActual)) {
                comentarioActual.setUsuario(usuario); //se reasigna el autor del comentario
            } else if (comentarioActual.getUsuario()!= null && comentarioActual.getUsuario().equals(usuario)) {
                comentarioActual.setUsuario(null);
            }
        }
        return nuevosComentarios;
    }
}
