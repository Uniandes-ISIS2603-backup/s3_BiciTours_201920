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
 * @author Michel Succar
 */
@Entity
public class EventoEntity extends BaseEntity implements Serializable{
        
    //nombre del evento
    private String nombre;
    //descripcion del evento
    private String descripcion;
    //hora en que inicia el evento
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date horaInicio;
    //hora en que termina el evento
    @Temporal(javax.persistence.TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class)
    private Date horaFin;
    
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
    public Date getHoraInicio( )
    {
        return this.horaInicio;
    }
    
    /**
     *
     * @param pHora a la que se va a iniciar el evento
     */
    public void setHoraInicio(Date pHora)
    {
        this.horaInicio = pHora;
    }
    
    /**
     *
     * @return hora en que termina el evento
     */
    public Date getHoraFin( )
    {
        return this.horaFin;
    }
    
    /**
     *
     * @param pHora en la que se va a tetrminar el evento
     */
    public void setHoraFin(Date pHora)
    {
        this.horaFin = pHora;
    }
}
