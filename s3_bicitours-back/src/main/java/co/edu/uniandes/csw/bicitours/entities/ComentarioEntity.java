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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author JuanRueda
 */

@Entity
public class ComentarioEntity extends BaseEntity implements Serializable{
    
    private String texto;
    
    private Integer calificacion;
    
    //@PodamExclude
    //@OneToMany(mappedBy = "head", cascade = CascadeType.PERSIST)
    //private List<ComentarioEntity> respuestas = new ArrayList<ComentarioEntity>();
    
    //@ManyToOne
    //private ComentarioEntity head;
    
    //@ManyToOne
    //private UsuarioEntity autor;
    
    @PodamExclude
    @ManyToOne
    private BlogEntity blog;
    
    public ComentarioEntity()
    {
        
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
     * @return the blog
     */
    public BlogEntity getBlog() {
        return blog;
    }

    /**
     * @param blog the blog to set
     */
    public void setBlog(BlogEntity blog) {
        this.blog = blog;
    }

    /**
     * @return the respuestas
     */
    //public List<ComentarioEntity> getRespuestas() {
    //    return respuestas;
    //}

    /**
     * @param respuestas the respuestas to set
     */
    //public void setRespuestas(List<ComentarioEntity> respuestas) {
    //    this.respuestas = respuestas;
    //}

    /**
     * @return the head
     */
    //public ComentarioEntity getHead() {
    //    return head;
    //}

    /**
     * @param head the head to set
     */
    //public void setHead(ComentarioEntity head) {
    //    this.head = head;
    //}

    /**
     * @return the autor
     */
    //public UsuarioEntity getAutor() {
    //    return autor;
    //}

    /**
     * @param autor the autor to set
     */
    //public void setAutor(UsuarioEntity autor) {
    //    this.autor = autor;
    //}
}
