/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import java.io.Serializable;

/**
 *
 * @author JuanRueda
 */
public class ComentarioDTO implements Serializable
{
    private Long id;
    private String texto;
    private Integer calificacion;
    //private UsuarioDTO usuario;
    private BlogDTO blog;
    //private TourDTO tour;
    
    public ComentarioDTO()
    {
        
    }
    
    public ComentarioDTO(ComentarioEntity comentario) 
    {
        if (comentario != null) {
            this.id = comentario.getId();
            this.texto = comentario.getTexto();
            this.calificacion = comentario.getCalificacion();
            //if (comentario.getUsuario() != null) 
            //{
            //    this.usuario = new UsuarioDTO(comentario.getUsuario());
            //}
            //else 
            //{
                //this.usuario = null;
            //}
            if (comentario.getBlog() != null) 
            {
                this.blog = new BlogDTO(comentario.getBlog());
            }
            else 
            {
                this.blog = null;
            }
            //if (comentario.getTour() != null) 
            //{
            //    this.tour = new TourDTO(comentario.getTour());
            //}
            //else 
            //{
                //this.tour = null;
            //}
        }
    }
    
    

    public ComentarioEntity toEntity() 
    {
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(this.getId());
        comentario.setTexto(this.getTexto());
        comentario.setCalificacion(this.getCalificacion());
        //if (this.usuario != null) 
        //{
        //    comentario.setUsuario(this.usuario.toEntity());
        //}
        if (this.blog != null) 
        {
            comentario.setBlog(this.blog.toEntity());
        }
        //if (this.tour != null) 
        //{
        //    comentario.setTour(this.tour.toEntity());
        //}
        return comentario;
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
     * @return the calificacion
     */
    public Integer getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }
    
    /**
     * @return the usuario.
     */
    //public UsuarioDTO getUsuario() {
    //    return usuario;
    //}

    /**
     * @param usuario the usuario to set
     */
    //public void setUsuario(UsuarioDTO usuario) {
    //    this.usuario = usuario;
    //}
    
    /**
     * @return the blog.
     */
    public BlogDTO getBlog() {
        return blog;
    }

    /**
     * @param blog the blog to set
     */
    public void setBlog(BlogDTO blog) {
        this.blog = blog;
    }
    
    /**
     * @return the tour.
     */
    //public TourDTO getTour() {
    //    return tour;
    //}

    /**
     * @param tour the tour to set
     */
    //public void setTour(TourDTO tour) {
    //    this.tour = tour;
    //}
    
}
