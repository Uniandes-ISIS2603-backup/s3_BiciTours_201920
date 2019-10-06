/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Juan Sebastián González Rojas
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable{
    //hola este es un comentario
    //Nombre del usuario
    private String nombre;
    //Código/Contrañesa/Password del usuario
    private String codigo;
    //Correo del usuario
    private String correo;
    //Estado de paz y salvo del usuario
    private boolean pago;
    //Clasificación como cliente o administrador
    private boolean esAdmin;
    //Deuda total del usuario
    private Integer deuda;
    @PodamExclude
    @OneToMany(mappedBy = "creador")
    private List<BlogEntity> misBlogs = new ArrayList<BlogEntity>();
    @PodamExclude
    @ManyToMany
    private List<BlogEntity> favoritos;
    public UsuarioEntity() {
        
    }
    /**
     * @return Nombre del usuario
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre Nuevo nombre del usuario
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return Código del usuario
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @param codigo Nuevo código del usuario
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * @return Booleano que determina si el usuario a realizado o no el pago
     */
    public boolean isPago() {
        return pago;
    }

    /**
     * @param pago Booleano para definir si el usuario a realizado o no el pago
     */
    public void setPago(boolean pago) {
        this.pago = pago;
    }

    /**
     * @return Booleano que determina el tipo de usuario (cliente o administrador)
     */
    public boolean isEsAdmin() {
        return esAdmin;
    }

    /**
     * @param esAdmin Definir si el usuario es de tipo Administrador
     */
    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    /**
     * @return Integer que contiene la deuda del usuario
     */
    public Integer getDeuda() {
        return deuda;
    }

    /**
     * @param deuda Definir la deuda del usuario
     */
    public void setDeuda(Integer deuda) {
        this.deuda = deuda;
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
     * @return the misBlogs
     */
    public List<BlogEntity> getMisBlogs() {
        return misBlogs;
    }

    /**
     * @param misBlogs the misBlogs to set
     */
    public void setMisBlogs(List<BlogEntity> misBlogs) {
        this.misBlogs = misBlogs;
    }

    /**
     * @return the favoritos
     */
    public List<BlogEntity> getFavoritos() {
        return favoritos;
    }

    /**
     * @param favoritos the favoritos to set
     */
    public void setFavoritos(List<BlogEntity> favoritos) {
        this.favoritos = favoritos;
    }
}
