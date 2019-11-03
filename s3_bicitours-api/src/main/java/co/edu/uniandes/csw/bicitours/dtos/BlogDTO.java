/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import java.io.Serializable;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
public class BlogDTO implements Serializable{

    private BlogDTO anterior;
    private BlogDTO siguiente;
    private Long id;
    private String texto;
    private String rutaImagen;
    private String rutaVideo;
    private Double calificacionPromedio;
    private String titulo;
    private TourDTO tour;
    private UsuarioDTO creador;
    public BlogDTO() {
    }
    public BlogDTO(BlogEntity blogEntity) {
    if (blogEntity != null) {
        this.id = blogEntity.getId();
        this.texto = blogEntity.getTexto();
        this.rutaImagen = blogEntity.getRutaImagen();
        this.rutaVideo = blogEntity.getRutaVideo();
        this.calificacionPromedio = blogEntity.getCalificacionPromedio();
        this.titulo = blogEntity.getTitulo();
        if (blogEntity.getAnterior() != null) {
            this.anterior = new BlogDTO(blogEntity.getAnterior());
        } else {
            this.anterior = null;
        }
        if (blogEntity.getSiguiente() != null) {
            this.siguiente = new BlogDTO(blogEntity.getSiguiente());
        } else {
            this.siguiente = null;
        }
        if (blogEntity.getCreador() != null) {
            this.creador = new UsuarioDTO(blogEntity.getCreador());
        } else {
            this.creador = null;
        }
        if (blogEntity.getTour() != null) {
            this.tour = new TourDTO(blogEntity.getTour());
        } else {
            this.tour = null;
        }        
    }
    }
        public BlogEntity toEntity() {
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setId(this.getId());
        blogEntity.setTexto(this.getTexto());
        blogEntity.setRutaImagen(this.getRutaImagen());
        blogEntity.setRutaVideo(this.getRutaVideo());
        blogEntity.setCalificacionPromedio(this.getCalificacionPromedio());
        blogEntity.setTitulo(this.getTitulo());
        if (this.getAnterior() != null) {
            blogEntity.setAnterior(this.getAnterior().toEntity());
        }
        if (this.getSiguiente() != null) {
            blogEntity.setSiguiente(this.getSiguiente().toEntity());
        }
        if (this.getCreador() != null) {
            blogEntity.setCreador(this.getCreador().toEntity());
        }
        if (this.getTour() != null) {
            blogEntity.setTour(this.getTour().toEntity());
        }
        return blogEntity;
    }

    /**
     * @return the anterior
     */
    public BlogDTO getAnterior() {
        return anterior;
    }

    /**
     * @param anterior the anterior to set
     */
    public void setAnterior(BlogDTO anterior) {
        this.anterior = anterior;
    }

    /**
     * @return the siguiente
     */
    public BlogDTO getSiguiente() {
        return siguiente;
    }

    /**
     * @param siguiente the siguiente to set
     */
    public void setSiguiente(BlogDTO siguiente) {
        this.siguiente = siguiente;
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
     * @return the tour
     */
    public TourDTO getTour() {
        return tour;
    }

    /**
     * @param tour the tour to set
     */
    public void setTour(TourDTO tour) {
        this.tour = tour;
    }

    /**
     * @return the creador
     */
    public UsuarioDTO getCreador() {
        return creador;
    }

    /**
     * @param creador the creador to set
     */
    public void setCreador(UsuarioDTO creador) {
        this.creador = creador;
    }
}
