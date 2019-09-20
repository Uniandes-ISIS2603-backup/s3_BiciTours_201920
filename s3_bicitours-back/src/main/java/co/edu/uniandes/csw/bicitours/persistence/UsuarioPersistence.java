/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.persistence;

import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Juan Sebastián González Rojas
 */

//permite que el contenedor maneje la creación y destrucción de esta clase
@Stateless
public class UsuarioPersistence {
    
    @PersistenceContext(unitName="bicitoursPU")
    protected EntityManager em; //realiza acceso a la base de datos
    
    //Comunicación 
    private static final Logger LOGGER= Logger.getLogger(UsuarioPersistence.class.getName());
    
     /**
     * Método para persistir la clase UsuarioEntity en la base de datos.
     *
     * @param usuarioEntity objeto UsuarioEntity que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public UsuarioEntity create(UsuarioEntity usuarioEntity){
        
        LOGGER.log(Level.INFO, "Creando nuevo blog en base de datos");
        em.persist(usuarioEntity);   
        return usuarioEntity;
    }
    
     /**
     * Busca y retorna un UsuarioEntity con el id que se envía de argumento
     * Retorna null en caso de no encontrarlo
     * @param usuarioId: id correspondiente al Usuario buscado.
     * @return un blog.
     */
    public UsuarioEntity find(Long usuarioId){
        LOGGER.log(Level.INFO, "Consultando el blog con id:", usuarioId);
        return em.find(UsuarioEntity.class, usuarioId);
    }
    
     /**
     * Retorna todas las clases de tipo UsuarioEntity en la base de datos
     * @return un blog.
     */
    public List<UsuarioEntity> findAll(){
        LOGGER.log(Level.INFO, "Solicitando una lista con todoos los Usuarios de la base de datos");
        TypedQuery<UsuarioEntity> query= em.createQuery("select u from UsuarioEntity u", UsuarioEntity.class);
        return query.getResultList();
    }
    
     /**
     * Actualiza un usuario con el objeto UsuarioEntity recibido por parámetro.
     *
     * @param usuarioEntity: El usuario viene con nuevos parámetros que deben unirse a la anterior versión.
     */
    public UsuarioEntity update(UsuarioEntity nuevoUsuarioEntity) {
        LOGGER.log(Level.INFO, "Actualizando el usuario con id:", nuevoUsuarioEntity.getId());
        return em.merge(nuevoUsuarioEntity);
    }
    
    /**
     * Borra un usuario de la base de datos cuyo id coincide con el recibido por parámetro
     * @param usuarioId: id del Usuario a eliminar.
     */
    public void delete(Long usuarioId) {
        LOGGER.log(Level.INFO, "Eliminando el usuario con id:", usuarioId);
        UsuarioEntity usuarioEntityElimitated = em.find(UsuarioEntity.class, usuarioId);
        em.remove(usuarioEntityElimitated);
    }
    
     /**
     * Busca si hay algun usuario con el nombre que se recibe como argumento
     *
     * @param pNombreUsuario: Nombre del Usuario buscado
     * @return null si no existe ningun usuario con el nombre del argumento. Si
     * existe alguno devuelve el primero encontrado.
     */
    public UsuarioEntity findByNombre(String pNombreUsuario) {
        LOGGER.log(Level.INFO, "Consultando usuarios por nombre: ", pNombreUsuario);
        // Se crea un query para buscar usuarios con el nombre que recibe el método como argumento. ":pNombreUsuario" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select u From UsuarioEntity u where u.nombre = :pNombreUsuario", UsuarioEntity.class);
        // Se remplaza el placeholder ":pNombreUsuario" con el valor del argumento 
        query = query.setParameter("pNombreUsuario", pNombreUsuario);
        // Se invoca el query se obtiene la lista resultado
        List<UsuarioEntity> sameNombre = query.getResultList();
        UsuarioEntity result;
        if (sameNombre == null) {
            result = null;
        } else if (sameNombre.isEmpty()) {
            result = null;
        } else {
            result = sameNombre.get(0);//si hay varios usuarios con el mismo nombre se escoge el primero, sin embargo esto no debería ocurrir
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar usuarios por nombre: ",pNombreUsuario );
        return result;
    }
    
    /**
     * Busca si hay algun usuario con el correo que se recibe como argumento
     *
     * @param pCorreo: Correo del Usuario buscado
     * @return null si no existe ningun usuario con el correo del argumento. Si
     * existe alguno devuelve el primero encontrado.
     */
    public UsuarioEntity findByCorreo(String pCorreo) {
        LOGGER.log(Level.INFO, "Consultando usuarios por correo: ", pCorreo);
        // Se crea un query para buscar usuarios con el correo que recibe el método como argumento. ":pCorreo" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select u From UsuarioEntity u where u.correo = :pCorreo", UsuarioEntity.class);
        // Se remplaza el placeholder ":pCorreo" con el valor del argumento 
        query = query.setParameter("pCorreo", pCorreo);
        // Se invoca el query se obtiene la lista resultado
        List<UsuarioEntity> sameCorreo = query.getResultList();
        UsuarioEntity result;
        if (sameCorreo == null) {
            result = null;
        } else if (sameCorreo.isEmpty()) {
            result = null;
        } else {
            result = sameCorreo.get(0);//si hay varios usuarios con el mismo correo se escoge el primero, sin embargo esto no debería ocurrir
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar usuarios por correo: ",pCorreo );
        return result;
    }
}
