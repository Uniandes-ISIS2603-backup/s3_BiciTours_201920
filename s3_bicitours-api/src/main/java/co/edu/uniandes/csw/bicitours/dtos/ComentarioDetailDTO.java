/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JuanRueda
 */
public class ComentarioDetailDTO extends ComentarioDTO implements Serializable{
    
    private List<ComentarioDTO> respuestas;

    public ComentarioDetailDTO() {
        super();
    }

    public ComentarioDetailDTO(ComentarioEntity comentario) 
    {
        super(comentario);
        if (comentario.getRespuestas() != null) 
        {
            respuestas = new ArrayList<>();
            for (ComentarioEntity respuesta : comentario.getRespuestas()) 
            {
                respuestas.add(new ComentarioDTO(respuesta));
            }
        }
    }

    @Override
    public ComentarioEntity toEntity() 
    {
        ComentarioEntity comentario = super.toEntity();
        if (getRespuestas() != null) 
        {
            List<ComentarioEntity> respuestasEntity = new ArrayList<>();
            for (ComentarioDTO dtoComentario : getRespuestas()) 
            {
                respuestasEntity.add(dtoComentario.toEntity());
            }
            comentario.setRespuestas(respuestasEntity);
        }
        return comentario;
    }

    /**
     * @return the respuestas
     */
    public List<ComentarioDTO> getRespuestas() {
        return respuestas;
    }

    /**
     * @param respuestas the respuestas to set
     */
    public void setRespuestas(List<ComentarioDTO> respuestas) {
        this.respuestas = respuestas;
    }
}
