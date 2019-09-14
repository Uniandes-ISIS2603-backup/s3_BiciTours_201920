/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.ComentarioPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author JuanRueda
 */

@Stateless
public class ComentarioLogic {
    
    @Inject
    private ComentarioPersistence persistence;
    
    public ComentarioEntity createComentario(ComentarioEntity comentario) throws BusinessLogicException
    {
        if (comentario != null)
        {
            if(comentario.getCalificacion() != null && comentario.getTexto() != null)
            {
                if(comentario.getCalificacion() == 0 && comentario.getTexto().equals(""))
                {
                    throw new BusinessLogicException("El comentario tiene que tener un mensaje y/o una calificación.");
                }
                else
                {
                    if (comentario.getCalificacion()!= 0 && (comentario.getCalificacion() < 1 || comentario.getCalificacion() > 5))
                    {
                        throw new BusinessLogicException("La calificación tiene que estar entre 1 y 5.");
                    }
                    if (!comentario.getTexto().equals("") && comentario.getTexto().length() > 250)
                    {
                        throw new BusinessLogicException("El mensaje no puede superar los 250 caracteres.");
                    }
                    comentario = persistence.create(comentario);
                }
            }
            else
            {
                throw new BusinessLogicException("El comentario tiene que tener un mensaje y/o una calificación.");
            }
        }
        else
        {
            throw new BusinessLogicException("El comentario no puede ser nulo");
        }
        return comentario;
    }
}
