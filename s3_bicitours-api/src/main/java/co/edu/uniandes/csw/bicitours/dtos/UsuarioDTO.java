/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import java.io.Serializable;

/**
 *
 * @author Juan Sebastián González Rojas
 */
public class UsuarioDTO implements Serializable{

    //Atributos de DTO 
    private Long id;
    private String nombre;
    private String correo;
    private String codigo;
    private boolean esAdmin;
    private boolean pago;
    
    public UsuarioDTO(){
        
    }
    
    public UsuarioDTO(UsuarioEntity usuarioEntity){
        if (usuarioEntity != null) {
            this.id = usuarioEntity.getId();
            this.nombre = usuarioEntity.getNombre();
            this.esAdmin=false;
            this.correo=usuarioEntity.getCorreo();
            this.codigo=usuarioEntity.getCodigo();
            this.pago=false;
        }
    }
    
    /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(this.getId());
        usuarioEntity.setNombre(this.getNombre());
        usuarioEntity.setCorreo(this.getCorreo());
        usuarioEntity.setCodigo(this.getCodigo());
        usuarioEntity.setEsAdmin(this.isEsAdmin());
        usuarioEntity.setPago(this.isPago());
        //usuarioEntity.setDeuda(this.getDeuda());
        return usuarioEntity;
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
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
