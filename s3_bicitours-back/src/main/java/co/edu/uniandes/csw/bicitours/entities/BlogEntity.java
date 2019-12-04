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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un blog en la persistencia y permite su serialización
 *
 * @author Oscar Julian Castañeda G.
 */
@Entity
public class BlogEntity extends BaseEntity implements Serializable {

    @PodamExclude
    @OneToMany(
            mappedBy = "blog")
    private List<ComentarioEntity> comentarios;
    @PodamExclude
    @ManyToMany
    private List<UsuarioEntity> usuarios;
    @PodamExclude
    @ManyToOne
    private TourEntity tour;
    @PodamExclude
    @ManyToOne
    private UsuarioEntity creador;
    private String texto;
    private String rutaImagen;
    private String rutaVideo;
    private Double calificacionPromedio;
    private String titulo;

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
    public Double getCalificacionPromedio() {
        return calificacionPromedio;
    }

    /**
     * @param calificacionPromedio the calificacionPromedio to set
     */
    public void setCalificacionPromedio(Double calificacionPromedio) {
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
     * @return the usuarios
     */
    public List<UsuarioEntity> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioEntity> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the tour
     */
    public TourEntity getTour() {
        return tour;
    }

    /**
     * @param tour the tour to set
     */
    public void setTour(TourEntity tour) {
        this.tour = tour;
    }

    /**
     * @return the creador
     */
    public UsuarioEntity getCreador() {
        return creador;
    }

    /**
     * @param creador the creador to set
     */
    public void setCreador(UsuarioEntity creador) {
        this.creador = creador;
    }

}
