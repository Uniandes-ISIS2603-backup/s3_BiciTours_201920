/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.RecomendacionEntity;
import java.io.Serializable;

/**
 *
 * @author Maria Clara Noguera
 */
public class RecomendacionDTO implements Serializable {

    private Long id;
    private String tipoBici;
    private String indumentaria;

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
     * Construye un nuevo RecomendacionDTO a partir de una entidad Recomendacion
     * dada
     *
     * @param recomendacion, es la entidad recomendacion que se va a convertir a
     * RecomendacionDTO
     */
    public RecomendacionDTO(RecomendacionEntity recomendacion) {
        if (recomendacion != null) {
            id = recomendacion.getId();
            indumentaria = recomendacion.getIndumentaria();
            tipoBici = recomendacion.getTipoBici();

        }

    }

    public RecomendacionDTO() {

    }

    /**
     * Convierte este objeto RecomendacionDTO en una entidad con los mismos
     * datos
     *
     * @return un RecomendacionEntity con los mismos datos de este
     * recomendacionDTO
     */
    public RecomendacionEntity toEntity() {
        RecomendacionEntity recomendacion = new RecomendacionEntity();
        recomendacion.setId(id);
        recomendacion.setIndumentaria(indumentaria);
        recomendacion.setTipoBici(tipoBici);
        return recomendacion;
    }
}
