/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Jhuliana Barrios
 */
public class TourDetailDTO extends TourDTO implements Serializable{
    
    private LinkedList<FotoDTO> fotos;
    
    /////////
    ///Aquí solo hay sets y gets
    ////////
    
    public void setFotos(LinkedList<FotoDTO> fs)
    {
        fotos = fs;
    } 
    
    public LinkedList<FotoDTO> getFotos()
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
        fotos = new LinkedList<FotoDTO>();
        for(FotoEntity f : tour.getFotos())
        {
            FotoDTO nueva = new FotoDTO(f);
            fotos.add(nueva);
        }
        }
    }
    
    public TourEntity toEntity()
    {
        TourEntity tour = super.toEntity();
        
        //Convierte el conjunto de fotos
        LinkedList<FotoEntity> fotosE = new LinkedList<FotoEntity>();
        for(FotoDTO f : fotos)
        {
            FotoEntity nueva = f.toEntity();
            fotosE.add(nueva);
        }
        tour.setFotos(fotosE);
        
        return tour;
        
    }
    
    
    
}
    
