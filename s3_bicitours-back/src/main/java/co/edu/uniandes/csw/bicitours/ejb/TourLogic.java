/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Jhuliana Barrios
 */
@Stateless
public class TourLogic {
    
    @Inject
    private TourPersistence tp;
    
    public TourEntity createTour(TourEntity tour) throws BusinessLogicException
    {
        if(tour.getNombre()== null)
            throw new BusinessLogicException("El nombre del tour debe existir");
        else{
            //Como el nombre del tour no es nulo ya se puede llamar un método que apunte hacia este
            String sinespacios = tour.getNombre().replace(" ", "");
            if(sinespacios.equals(""))
                throw new BusinessLogicException("El nombre del tour no debe ser vacio");
        }
        
        if(tour.getLugar() == null)
            throw new BusinessLogicException("El sitio del tour debe existir");
        else{
            //Como el sitio del tour no es nulo ya se puede llamar un método que apunte hacia este
            String sinespacios = tour.getLugar().replace(" ", "");
            if(sinespacios.equals(""))
                throw new BusinessLogicException("El lugar del tour no debe ser vacio");
        }
            
        if(tour.getFecha() == null)
            throw new BusinessLogicException("El tour debe tener una fecha");
        else if(tour.getFecha().compareTo(new Date()) <= 0 )//Como la fecha no es nula ya se puede llamar un método que apunte hacia esta
            throw new BusinessLogicException("La fecha del tour no puede ser antes del presente día ni al instante en que se está creando");
        
        
        if(tour.getDescripcion() == null )
            throw new BusinessLogicException("La descripción del tour debe existir");
        else{
            String sinespacios = tour.getDescripcion().replace(" ", "");
            if(sinespacios.equals(""))
                throw new BusinessLogicException("La descripción del tour no debe estar vacia");
        }
        
        
        if(tour.getDuracion() <= 0)
            throw new BusinessLogicException("El tour debe tener una duración positiva y en minutos");
        
        if(tour.getDificultad() == null )//El usuario solo puede escoger entre dificultad media, alta o baja, si es nula no escogió ninguna y violó la regla de negocio
            throw new BusinessLogicException("La dificultad debe ser: media, alta, o baja");
        
        if(tour.getCosto() < 0)
            throw new BusinessLogicException("El costo del tour debe ser mayor o igual a 0");
            
        
        tour.setTerminado(false);
        tour = tp.create(tour);
        return tour;
        
    }
    
}
