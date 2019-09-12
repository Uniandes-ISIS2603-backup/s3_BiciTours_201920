/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.entities;

import co.edu.uniandes.csw.bicitours.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    
    public void setTerminado(boolean ter)
    {
    terminado = ter;
    }
    
    public boolean getTerminado()
    {
    return terminado;
    }
    

    
}