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
public class PagoDTO extends UsuarioDTO implements Serializable{

    //DTO para el cambio del estado de pago del usuario
    private boolean pago;
    
    public PagoDTO (){
    }
    /**
     * @return the pago
     */
    public boolean isPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(boolean pago) {
        this.pago = pago;
    }
}
