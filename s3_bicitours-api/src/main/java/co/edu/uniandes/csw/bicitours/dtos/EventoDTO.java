/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Michel Succar
 */
public class EventoDTO implements Serializable{
    
    private Long id;
    private String nombre;
    private String descripcion;
    private Long horaInicio;
    private Long horaFin;
    private TourDTO tour;

    public EventoDTO()
    {
        
    }
    
    public EventoDTO(EventoEntity evento) 
    {
        if (evento != null) 
        {
            this.id = evento.getId();
            this.nombre = evento.getNombre();
            this.descripcion = evento.getDescripcion();
            this.horaInicio = evento.getHoraInicio();
            this.horaFin = evento.getHoraFin();
            if (evento.getTour() != null) 
            {
                this.tour = new TourDTO(evento.getTour());
            }
            else 
            {
                this.tour = null;
            }
        }
    }
    
    public EventoEntity toEntity() 
    {
        EventoEntity evento = new EventoEntity();
        evento.setId(this.getId());
        evento.setNombre(this.getNombre());
        evento.setDescripcion(this.getDescripcion());
        evento.setHoraInicio(this.getHoraInicio());
        evento.setHoraFin(this.getHoraFin());
        if (this.tour != null) 
        {
            evento.setTour(this.tour.toEntity());
        }
        return evento;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the horaInicio
     */
    public Long getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(Long horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horaFin
     */
    public Long getHoraFin() {
        return horaFin;
    }

    /**
     * @param horaFin the horaFin to set
     */
    public void setHoraFin(Long horaFin) {
        this.horaFin = horaFin;
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
    
    
    
}
