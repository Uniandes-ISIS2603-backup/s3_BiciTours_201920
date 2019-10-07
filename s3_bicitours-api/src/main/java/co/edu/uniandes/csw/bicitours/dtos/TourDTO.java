/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Jhuliana Barrios
 */
public class TourDTO implements Serializable{
    
    
    ////Declaración de atributos
    
    private Long id;
    
    private String nombre;
    
    private String lugar;

    private String descripcion;

    private TourEntity.Dificultad dificultad;

    private Date fecha;

    private int duracion;

    private int costo;
    
    private boolean terminado;
        
    /////////
    ///Aquí solo hay sets y gets
    ////////

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDificultad(TourEntity.Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    public void setTerminado(boolean terminado) {
        this.terminado = terminado;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLugar() {
        return lugar;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TourEntity.Dificultad getDificultad() {
        return dificultad;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getCosto() {
        return costo;
    }

    public boolean isTerminado() {
        return terminado;
    }
    
    ////////////
    ///Aquí hay métodos propios de un DTO
    ///////////
    
    /**
     * Constructor sin parámetros
     */
    public TourDTO()
    {
    }
    
    /**
     * Construye un nuevo TourDTO a partir de una entidad Tour dada
     * @param tour, es la entidad tour que se va a convertir a tourDTO
     */
    public TourDTO(TourEntity tour)
    {
        if(tour != null){
            nombre = tour.getNombre();
            terminado = tour.getTerminado();
            lugar = tour.getLugar();
            id = tour.getId();
            fecha = tour.getFecha();
            duracion = tour.getDuracion();
            dificultad = tour.getDificultad();
            descripcion = tour.getDescripcion();
            costo = tour.getCosto();
        }
        
    }
    
    /**
     * Convierte este objeto TourDTO en una entidad con los mismos datos
     * @return un TourEntity con los mismos datos de este tourDTO
     */
    public TourEntity toEntity()
    {
        TourEntity tour = new TourEntity();
        tour.setCosto(costo);
        tour.setDescripcion(descripcion);
        tour.setDificultad(dificultad);
        tour.setDuracion(duracion);
        tour.setFecha(fecha);
        tour.setId(id);
        tour.setLugar(lugar);
        tour.setTerminado(terminado);
        tour.setNombre(nombre);
        
        return tour;
    }
}
