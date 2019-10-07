/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.entities;

import co.edu.uniandes.csw.bicitours.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Jhuliana Barrios
 */
@Entity
public class TourEntity extends BaseEntity implements Serializable {
//
    private static final long serialVersionUID = 1L;
    
    public enum Dificultad{
    ALTA,
    MEDIA,
    BAJA
    };
    
    private String nombre;

    private String lugar;

    private String descripcion;

    private Dificultad dificultad;

    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date fecha;

    private int duracion;

    private int costo;
    
    private boolean terminado;
    
    @PodamExclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ComentarioEntity> comentariosTour = new ArrayList<ComentarioEntity>();
    
    @PodamExclude
    @OneToMany(mappedBy = "tour")
    private List<BlogEntity> blogs = new ArrayList<BlogEntity>();
    public TourEntity()
    {
    }
    
    public void setNombre(String nomb) {
        this.nombre = nomb;
    }
    

    public String getNombre() {
        return this.nombre;
    }

    public void setLugar(String nomb) {
        this.lugar = nomb;
    }

    public String getLugar() {
        return this.lugar;
    }

    public void setDescripcion(String desc) {
        this.descripcion = desc;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDificultad(Dificultad dif) {
        this.dificultad = dif;
    }

    public Dificultad getDificultad() {
        return this.dificultad;
    }

    public void setFecha(Date fech)
    {
        this.fecha = fech;
    }

    public Date getFecha()
    {
        return this.fecha;
    }

    public void setDuracion(int dur)
    {
        this.duracion = dur;
    }

    public int getDuracion()
    {
        return this.duracion;
    }

    public void setCosto(int cost)
    {
        this.costo = cost;
    }

    public int getCosto()
    {
        return this.costo;
    }
    
    /**
     * @return the terminado
     */
    public boolean getTerminado() {
        return terminado;
    }
    
    public void setTerminado(boolean ter)
    {
    terminado = ter;
    }

    /**
     * @return the comentariosTour
     */
    public List<ComentarioEntity> getComentariosTour() {
        return comentariosTour;
    }

    /**
     * @param comentariosTour the comentariosTour to set
     */
    public void setComentariosTour(List<ComentarioEntity> comentariosTour) {
        this.comentariosTour = comentariosTour;
    }

    /**
     * @return the blogs
     */
    public List<BlogEntity> getBlogs() {
        return blogs;
    }

    /**
     * @param blogs the blogs to set
     */
    public void setBlogs(List<BlogEntity> blogs) {
        this.blogs = blogs;
    }
}