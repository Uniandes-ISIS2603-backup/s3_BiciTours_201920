/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jhuliana Barrios
 */
public class TourDetailDTO extends TourDTO implements Serializable{
    
    private List<FotoDTO> fotos;
    private List<BlogDTO> blogs;
    
    private List<EventoDTO> eventos;
    
    /////////
    ///Aquí solo hay sets y gets
    ////////
        /**
     * @return the eventos
     */
    public List<EventoDTO> getEventos() {
        return eventos;
    }

    /**
     * @param eventos the eventos to set
     */
    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }
    
    public void setFotos(List<FotoDTO> fs)
    {
        fotos = fs;
    } 
    
    public List<FotoDTO> getFotos()
    {
        return fotos;
    }
    
    ////////////
    ///Aquí hay métodos propios de un DetailDTO
    ///////////
    
    /**
     * Constructor sin parámetros
     */
    public TourDetailDTO()
    {
        super();
    }
    
    /**
     * Constructor con parámetros
     * @param tour 
     */
    public TourDetailDTO(TourEntity tour)
    {
        super(tour);
        
        if(tour != null){
        
         //Convierte el conjunto de fotos
        fotos = new ArrayList<>();
        for(FotoEntity f : tour.getFotos())
        {
            FotoDTO nueva = new FotoDTO(f);
            fotos.add(nueva);
        }
        blogs = new ArrayList<>();
        for(BlogEntity f : tour.getBlogs())
        {
            BlogDTO nueva = new BlogDTO(f);
            blogs.add(nueva);
        }
                if (tour.getEventosTour() != null) {
            eventos = new ArrayList<>();
            for (EventoEntity entityEvento : tour.getEventosTour()) {
                eventos.add(new EventoDTO(entityEvento));
            }
        }
        }
        
        //Convierte el conjunto de eventos

    }
    
    @Override
    public TourEntity toEntity()
    {
        TourEntity tour = super.toEntity();
        
        //Convierte el conjunto de fotos
        List<FotoEntity> fotosE = new ArrayList<>();
        for(FotoDTO f : fotos)
        {
            FotoEntity nueva = f.toEntity();
            fotosE.add(nueva);
        }
        tour.setFotos(fotosE);

        
        if (getEventos() != null) {
            List<EventoEntity> eventosEntity = new ArrayList<>();
            for (EventoDTO dtoEvento : getEventos()) {
                eventosEntity.add(dtoEvento.toEntity());
            }
            tour.setEventosTour(eventosEntity);
        }
        

        List<BlogEntity> blogsE = new ArrayList<>();
        for(BlogDTO f : blogs)
        {
            BlogEntity nueva = f.toEntity();
            blogsE.add(nueva);
        }
        tour.setBlogs(blogsE);        

        return tour;
        
    }

    /**
     * @return the blogs
     */
    public List<BlogDTO> getBlogs() {
        return blogs;
    }

    /**
     * @param blogs the blogs to set
     */
    public void setBlogs(List<BlogDTO> blogs) {
        this.blogs = blogs;
    }
    
    
    
}
    
