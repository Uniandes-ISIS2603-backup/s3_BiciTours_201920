/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
import java.io.Serializable;

/**
 *
 * @author Maria Clara Noguera Echeverri
 */
public class SeguroDTO implements Serializable {

    private Long id;
    private String tipo;
    private String caracteristicas;
    private String condiciones;

    public SeguroDTO() {
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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Construye un nuevo SeguroDTO a partir de una entidad Seguro dada
     *
     * @param seguro, es la entidad seguro que se va a convertir a SeguroDTO
     */
    public SeguroDTO(SeguroEntity seguro) {
        if (seguro != null) {
            id = seguro.getId();
            tipo = seguro.getTipo();
            caracteristicas = seguro.getCaracteristicas();
            condiciones = seguro.getCondiciones();
        }

    }

    /**
     * Convierte este objeto SeguroDTO en una entidad con los mismos datos
     *
     * @return un SeguroEntity con los mismos datos de este seguroDTO
     */
    public SeguroEntity toEntity() {
        SeguroEntity seguro = new SeguroEntity();
        seguro.setId(id);
        seguro.setTipo(tipo);
        seguro.setCaracteristicas(caracteristicas);
        seguro.setCondiciones(condiciones);
        return seguro;
    }

    /**
     * @return the caracteristicas
     */
    public String getCaracteristicas() {
        return caracteristicas;
    }

    /**
     * @param caracteristicas the caracteristicas to set
     */
    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    /**
     * @return the condiciones
     */
    public String getCondiciones() {
        return condiciones;
    }

    /**
     * @param condiciones the condiciones to set
     */
    public void setCondiciones(String condiciones) {
        this.condiciones = condiciones;
    }
}
