/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
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
        
        usuario=persistencia.create(usuario); //Creación de usuario en la persistencia
        return usuario;
    }
}
