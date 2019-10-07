/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import java.io.Serializable;

/**
 *
 * @author Jhuliana Barrios
 */
public class FotoDTO implements Serializable{
    
    /////Atributos del dto
    private Long id;
    private String ruta;

    /////////
    ///Sets y gets de los atributos
    ////////
    public void setId(Long id) {
        this.id = id;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Long getId() {
        return id;
    }

    public String getRuta() {
        return ruta;
    }
    
    /////////
    ///Métodos propios del DTO
    ////////
    
    /**
     * Constructor sin parámetros
     */
    public FotoDTO()
    {
    }
    
    /**
     * Construye un nuevo FotoDTO a partir de un FotoEntity dado
     * @param f la entidad de la foto
     */
    public FotoDTO(FotoEntity f)
    {
        if (f != null) {
            f.setId(id);
            f.setRuta(ruta);
        }
    }
    
    /**
     * Convierte esta foto a una entidad
     * @return la entidad con los mismos datos de esta foto
     */
    public FotoEntity toEntity()
    {
        FotoEntity f = new FotoEntity();
        f.setId(id);
        f.setRuta(ruta);
        return f;
    }
}
