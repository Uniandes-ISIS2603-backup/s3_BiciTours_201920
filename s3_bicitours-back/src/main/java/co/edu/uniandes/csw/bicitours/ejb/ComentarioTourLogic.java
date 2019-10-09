/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.persistence.ComentarioPersistence;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
import javax.inject.Inject;

/**
 *
 * @author JuanRueda
 */
public class ComentarioTourLogic {
    @Inject
    private ComentarioPersistence persistenciaC;

    @Inject
    private TourPersistence persistenciaT;

    /**
     * Asocia un tour existente a un comentario
     *
     * @param comentarioId Identificador de la instancia de comentario
     * @param tourId Identificador de la instancia de tour
     * @return Instancia de TourEntity que fue asociada a comentario
     */
    public TourEntity setTour(Long comentarioId, Long tourId) {
        TourEntity entidadU = persistenciaT.find(tourId);
        ComentarioEntity entidadC = persistenciaC.find(comentarioId);
        entidadC.setTour(entidadU);
        return persistenciaT.find(tourId);
    }

    /**
     * Obtiene una instancia de TourEntity asociada a una instancia de comentario
     *
     * @param comentarioId Identificador de la instancia de comentario
     * @return La entidad del Autor asociada al libro
     */
    public TourEntity getTour(Long comentarioId) {
        TourEntity tour = persistenciaC.find(comentarioId).getTour();
        return tour;
    }

    /**
     * Desasocia un tour existente de un comentario existente
     *
     * @param comentarioId Identificador de la instancia de comentario
     */
    public void removeTour(Long comentarioId) {
        ComentarioEntity entidadC = persistenciaC.find(comentarioId);
        entidadC.setTour(null);
    }
}