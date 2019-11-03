/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Jhuliana Barrios
 */
@Entity
public class FotoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ruta;

    @PodamExclude
    @ManyToOne()
    TourEntity tour;

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String r) {
        ruta = r;
    }

    public TourEntity getTour() {
        return tour;
    }

    public void setTour(TourEntity t) {
        tour = t;
    }

}
