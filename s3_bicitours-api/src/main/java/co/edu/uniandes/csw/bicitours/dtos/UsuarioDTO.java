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
public class UsuarioDTO implements Serializable {

    //Atributos de DTO 
    private Long id;
    private String nombre;
    private String correo;
    private String password;
    private Boolean esAdmin;
    private Boolean pago;
    private Integer deuda;

    public UsuarioDTO() {

    }

    public UsuarioDTO(UsuarioEntity usuarioEntity) {
        if (usuarioEntity != null) {
            this.id = usuarioEntity.getId();
            this.nombre = usuarioEntity.getNombre();
            this.esAdmin = false;
            this.correo = usuarioEntity.getCorreo();
            this.password = usuarioEntity.getPassword();
            this.pago = false;
            this.deuda = usuarioEntity.getDeuda();
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
        usuarioEntity.setPassword(this.getPassword());
        usuarioEntity.setEsAdmin(this.isEsAdmin());
        usuarioEntity.setPago(this.isPago());
        usuarioEntity.setDeuda(this.getDeuda());
        return usuarioEntity;
    }

    /**
     * @return the pago
     */
    public Boolean isPago() {
        return getPago();
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(Boolean pago) {
        this.pago = pago;
    }

    /**
     * @return the esAdmin
     */
    public Boolean isEsAdmin() {
        return getEsAdmin();
    }

    /**
     * @param esAdmin the esAdmin to set
     */
    public void setEsAdmin(Boolean esAdmin) {
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
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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

    /**
     * @return the esAdmin
     */
    public Boolean getEsAdmin() {
        return esAdmin;
    }

    /**
     * @return the pago
     */
    public Boolean getPago() {
        return pago;
    }

    /**
     * @return the deuda
     */
    public Integer getDeuda() {
        return deuda;
    }

    /**
     * @param deuda the deuda to set
     */
    public void setDeuda(Integer deuta) {
        this.deuda = deuta;
    }
}
