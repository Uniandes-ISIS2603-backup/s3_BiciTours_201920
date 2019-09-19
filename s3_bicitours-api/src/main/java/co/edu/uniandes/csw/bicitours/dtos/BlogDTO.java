/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;

/**
 *
 * @author Estudiante
 */
public class BlogDTO {

    private BlogDTO anterior;
    private BlogDTO siguiente;
    private long id;
    private String texto;
    private String rutaImagen;
    private String rutaVideo;
    private double calificacionPromedio;
    private String titulo;
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
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
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
}
