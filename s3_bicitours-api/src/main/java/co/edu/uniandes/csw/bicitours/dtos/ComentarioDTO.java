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
    //private UsuarioDTO autor;
    //private ComentarioDTO head;
    
    public ComentarioDTO()
    {
        
    }
    
    public ComentarioDTO(ComentarioEntity comentario) 
    {
        if (comentario != null) {
            this.id = comentario.getId();
            this.texto = comentario.getTexto();
            this.calificacion = comentario.getCalificacion();
            //if (comentario.getAutor() != null) 
            //{
            //    this.autor = new UsuarioDTO(comentario.getAutor());
            //}
            //else 
            //{
                //this.autor = null;
            //}
            //if (comentario.getHead() != null) 
            //{
            //    this.head = new ComentarioDTO(comentario.getHead());
            //}
            //else 
            //{
                //this.head = null;
            //}
        }
    }
    
    

    public ComentarioEntity toEntity() 
    {
        ComentarioEntity comentario = new ComentarioEntity();
        comentario.setId(this.getId());
        comentario.setTexto(this.getTexto());
        comentario.setCalificacion(this.getCalificacion());
        //if (this.autor != null) 
        //{
        //    comentario.setAutor(this.autor.toEntity());
        //}
        //if (this.head != null) 
        //{
        //    comentario.setHead(this.head.toEntity());
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
     * @return the autor.
     */
    //public UsuarioDTO getAutor() {
    //    return autor;
    //}

    /**
     * @param autor the autor to set
     */
    //public void setAutor(UsuarioDTO autor) {
    //    this.autor = autor;
    //}
    
    /**
     * @return the head.
     */
    //public ComentarioDTO getHead() {
    //    return head;
    //}

    /**
     * @param head the head to set
     */
    //public void setHead(ComentarioDTO head) {
    //    this.head = head;
    //}
}
