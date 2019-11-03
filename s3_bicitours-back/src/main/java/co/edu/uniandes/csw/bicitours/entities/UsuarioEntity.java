/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Juan Sebastián González Rojas
 */
@Entity
public class UsuarioEntity extends BaseEntity implements Serializable {

    //Nombre del usuario
    private String nombre;
    //Código/Contrañesa/Password del usuario
    private String codigo;
    //Correo del usuario
    private String correo;
    //Estado de paz y salvo del usuario
    private Boolean pago;
    //Clasificación como cliente o administrador
    private Boolean esAdmin;
    //Deuda total del usuario
    private Integer deuda;

    //Relaciones entre clases:
    //Relación de autoría con comentario
    @PodamExclude
    @OneToMany(mappedBy = "usuario",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<ComentarioEntity> comentarios;
    //Relación de toures visitados con usuario

    @PodamExclude
    @ManyToMany
    private List<TourEntity> visitados;

    //Relación de posesión con seguro
    @PodamExclude
    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<SeguroEntity> seguros;

    //Relación de autoría con blog
    @PodamExclude
    @OneToMany(mappedBy = "creador")
    private List<BlogEntity> misBlogs;
    //Relación de favoritos con blog
    @PodamExclude
    @ManyToMany(mappedBy = "usuarios")
    private List<BlogEntity> favoritos;

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
     * @return the pago
     */
    public Boolean isPago() {
        return pago;
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
        return esAdmin;
    }

    /**
     * @param esAdmin Definir si el usuario es de tipo Administrador
     */
    public void setEsAdmin(Boolean esAdmin) {
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

    ///////////////////////MÉTODOS RELACIONES ENTRE ENTITIES////////////////////
    /**
     * @return the comentarios
     */
    public List<ComentarioEntity> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioEntity> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * @param comentario the comentario to add
     */
    public void addComentario(ComentarioEntity comentario) {
        this.comentarios.add(comentario);
    }

    /**
     * @return the visitados
     */
    public List<TourEntity> getVisitados() {
        return visitados;
    }

    /**
     * @param visitados the visitados to set
     */
    public void setVisitados(List<TourEntity> visitados) {
        this.visitados = visitados;
    }

    /**
     * @return the seguros
     */
    public List<SeguroEntity> getSeguros() {
        return seguros;
    }

    /**
     * @param seguros the seguros to set
     */
    public void setSeguros(List<SeguroEntity> seguros) {
        this.seguros = seguros;
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
