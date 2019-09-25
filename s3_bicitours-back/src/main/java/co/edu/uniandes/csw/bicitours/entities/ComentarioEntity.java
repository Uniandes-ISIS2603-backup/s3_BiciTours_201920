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
    
    @PodamExclude
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<ComentarioEntity> respuestas = new ArrayList<ComentarioEntity>();
    
    /*@ManyToOne
    private ComentarioEntity head;
    */
    
    //@PodamExclude
    //@ManyToOne
    //private UsuarioEntity usuario;
    
    @PodamExclude
    @ManyToOne
    private BlogEntity blog;
    
    @PodamExclude
    @ManyToOne
    private TourEntity tour;
    
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
     * @return the respuestas
     */
    public List<ComentarioEntity> getRespuestas() {
        return respuestas;
    }

    /**
     * @param respuestas the respuestas to set
     */
    public void setRespuestas(List<ComentarioEntity> respuestas) {
        this.respuestas = respuestas;
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
     * @return the usuario
     */
    //public UsuarioEntity getUsuario() {
    //    return usuario;
    //}

    /**
     * @param usuario the usuario to set
     */
    //public void setUsuario(UsuarioEntity usuario) {
    //    this.usuario = usuario;
    //}
    
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
    
    
}
