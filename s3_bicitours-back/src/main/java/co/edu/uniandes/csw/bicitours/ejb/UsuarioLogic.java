/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Juan Sebastián González Rojas
 */
@Stateless
public class UsuarioLogic {
    @Inject
    private UsuarioPersistence persistencia;
    
    public UsuarioEntity create (UsuarioEntity usuario) throws BusinessLogicException{
        //En la creación del usuario el nombre debe ser diferente de nulo
        if(usuario.getNombre()==(null)){
            throw new BusinessLogicException("El nombre del usuario es nulo.");
        }
        //En la creación del usuario el código de su cuenta debe ser diferente de nulo
        if(usuario.getCodigo()==(null)){
            throw new BusinessLogicException("El código del usuario es nulo.");
        }
        //En la creación del usuario el correo de su cuenta debe ser diferente de nulo
        if(usuario.getCorreo()==(null)){
            throw new BusinessLogicException("El correo del usuario es nulo.");
        }
        //En la creacción del usuario el nombre no debe haber sido utilizado antes
        if(persistencia.findByNombre(usuario.getNombre())!=null){
            throw new BusinessLogicException("El nombre ingresado ya ha sido utilizado.");
        }
        if(persistencia.findByCorreo(usuario.getCorreo())!=null){
            throw new BusinessLogicException("El correo ingresado ya ha sido utilizado.");
        }
        usuario=persistencia.create(usuario); //Creación de usuario en la persistencia
        return usuario;
    }
     /**
     * Obtener un Usuario.
     *
     * @param usuarioId: id del usuario para que sea buscado en la base de datos
     * @return el usuario buscado.
     */
    public UsuarioEntity getUsuario(Long usuariosId) {
        UsuarioEntity usuarioEntity = persistencia.find(usuariosId);
        return usuarioEntity;
    }
    
    /**
     * Obtener Usuarioss.
     * @return usuarios.
     */
    public List<UsuarioEntity> getUsuarios() {
        List<UsuarioEntity> usuariosEntity = persistencia.findAll();
        return usuariosEntity;
    }
    
    /**
     * Obtener un Usuario por su nombre.
     *
     * @param usuarioName: nombre del usuario para que sea buscado en la base de datos
     * @return el usuario buscado.
     */
    public UsuarioEntity getUsuarioByNombre(String usuariosName) {
        UsuarioEntity usuarioEntity = persistencia.findByNombre(usuariosName);
        return usuarioEntity;
    }
    
    /**
     * Obtener un Usuario por su correo.
     *
     * @param usuarioCorreo: correo del usuario para que sea buscado en la base de datos
     * @return el usuario buscado.
     */
    public UsuarioEntity getUsuarioByCorreo(String usuariosCorreo) {
        UsuarioEntity usuarioEntity = persistencia.findByCorreo(usuariosCorreo);
        return usuarioEntity;
    }
    
    /**
     * Actualizar un Usuario.
     * @param usuarioId: id del usuario para que sea buscado en la base de datos
     * @param usuarioEntity: información de usuario a actualizar.
     * @return el usuario con los cambios actualizados en la base de datos.
     */
    public UsuarioEntity updateUsuario(Long usuarioId, UsuarioEntity usuarioEntity) {
        UsuarioEntity newUsuario = persistencia.update(usuarioEntity);
        return newUsuario;
    }
    
    /**
     * Eliminar un Usuario.
     * @param usuarioId: id del usuario para que sea buscado en la base de datos
     * @return el usuario con los cambios actualizados en la base de datos.
     */
    public void deleteUsuario(Long usuarioId) {
        persistencia.delete(usuarioId);
    }
}
