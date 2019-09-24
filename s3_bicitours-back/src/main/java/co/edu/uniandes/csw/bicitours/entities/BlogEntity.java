/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un blog en la persistencia y permite su serialización
 *
 * @author Oscar Julian Castañeda G.
 */
@Entity
public class BlogEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToOne
    private BlogEntity anterior;
    @PodamExclude
    @OneToOne
    private BlogEntity siguiente;
    @PodamExclude
    @OneToMany(
        mappedBy = "blog",
    	cascade = CascadeType.PERSIST,
    	fetch = FetchType.EAGER,
    	orphanRemoval = true
    )
    private List<ComentarioEntity> comentarios;
    
    private String texto;
    private String rutaImagen;
    private String rutaVideo;
    private double calificacionPromedio;
    private String titulo;

    public BlogEntity() {

    }

    /**
     * @return the texto
     */
    public String getTexto() {
        return texto;
    }

    /**
     * @param texto the texto to set
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     * @return the rutaImagen
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * @param rutaImagen the rutaImagen to set
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    /**
     * @return the rutaVideo
     */
    public String getRutaVideo() {
        return rutaVideo;
    }

    /**
     * @param rutaVideo the rutaVideo to set
     */
    public void setRutaVideo(String rutaVideo) {
        this.rutaVideo = rutaVideo;
    }

    /**
     * @return the calificacionPromedio
     */
    public double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    /**
     * @param calificacionPromedio the calificacionPromedio to set
     */
    public void setCalificacionPromedio(double calificacionPromedio) {
        this.calificacionPromedio = calificacionPromedio;
    }

    /**
     * @return the titulo
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * @param titulo the titulo to set
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * @return the anterior
     */
    public BlogEntity getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(BlogEntity anterior) {
        this.anterior = anterior;
    }

    /**
     * @return the siguiente
     */
    public BlogEntity getSiguiente() {
        return siguiente;
    }

    /**
     * @param siguiente the siguiente to set
     */
    public void setSiguiente(BlogEntity siguiente) {
        this.siguiente = siguiente;
    }

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

}
