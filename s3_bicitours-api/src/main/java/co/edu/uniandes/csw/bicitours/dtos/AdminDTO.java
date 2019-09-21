/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import java.io.Serializable;

/**
 *
 * @author Juan Sebastián González Rojas
 */
public class AdminDTO extends UsuarioDTO implements Serializable{

    //DTO utilizado para actualizar el tipo de usuario logeado
    private boolean esAdmin;
    
    public AdminDTO() {}
    /**
     * @return the esAdmin
     */
    public boolean isEsAdmin() {
        return esAdmin;
    }

    /**
     * @param esAdmin the esAdmin to set
     */
    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }
}
