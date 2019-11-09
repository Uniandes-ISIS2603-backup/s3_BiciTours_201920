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
 * @author Maria Clara Noguera Echeverri
 */
@Entity
public class RecomendacionEntity extends BaseEntity implements Serializable {

    private String tipoBici;
    private String indumentaria;
    @PodamExclude
    @ManyToOne
    private TourEntity tour;

    /**
     * @return the tipoBici
     */
    public String getTipoBici() {
        return tipoBici;
    }

    /**
     * @param tipoBici the tipoBici to set
     */
    public void setTipoBici(String tipoBici) {
        this.tipoBici = tipoBici;
    }

    /**
     * @return the indumentaria
     */
    public String getIndumentaria() {
        return indumentaria;
    }

    /**
     * @param indumentaria the indumentaria to set
     */
    public void setIndumentaria(String indumentaria) {
        this.indumentaria = indumentaria;
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
