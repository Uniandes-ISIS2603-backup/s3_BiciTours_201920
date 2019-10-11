/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.entities;

import co.edu.uniandes.csw.bicitours.podam.DateStrategy;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Michel Succar
 */
@Entity
public class EventoEntity extends BaseEntity implements Serializable{
        
    //nombre del evento
    private String nombre;
    //descripcion del evento
    private String descripcion;
    //hora en que inicia el evento
    private Long horaInicio;
    //hora en que termina el evento
    private Long horaFin;
    //Tour al que se relaciona el evento
    @PodamExclude
    @ManyToOne (cascade = CascadeType.PERSIST)
    private TourEntity tour;
    
    public EventoEntity()
    {
        
    }
    
    /**
     *
     * @return nombre
     */
    public String getNombre( )
    {
        return nombre;
    }
    
    /**
     *
     * @param pNombre nombre que darle al evento
     */
    public void setNombre(String pNombre)
    {
        nombre = pNombre;
    }
    
    /**
     *
     * @return descripcion
     */
    public String getDescripcion( )
    {
        return descripcion;
    }
    
    /**
     *
     * @param pDescripcion descripcion que darle al evento
     */
    public void setDescripcion(String pDescripcion)
    {
        descripcion = pDescripcion;
    }
    
    /**
     *
     * @return hora de inicio del evento
     */
    public Long getHoraInicio( )
    {
        return this.horaInicio;
    }
    
    /**
     *
     * @param pHora a la que se va a iniciar el evento
     */
    public void setHoraInicio(Long pHora)
    {
        this.horaInicio = pHora;
    }
    
    /**
     *
     * @return hora en que termina el evento
     */
    public Long getHoraFin( )
    {
        return this.horaFin;
    }
    
    /**
     *
     * @param pHora en la que se va a tetrminar el evento
     */
    public void setHoraFin(Long pHora)
    {
        this.horaFin = pHora;
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
}
