/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.FotoDTO;
import co.edu.uniandes.csw.bicitours.ejb.FotoLogic;
import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Jhuliana Barrios
 */

@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FotoResource {
    
    private static final String RECURSO = "El recurso /tours/";
    private static final String NOEXISTE = " no existe.";
    private static final String FOTOS = "/fotos/";
    @Inject
    private FotoLogic fotoLogic;

    /**
     * Crea una nueva foto con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param toursId El ID del Tour del cual se le agrega la foto
     * @param foto {@link FotoDTO} - La foto que se desea guardar.
     * @return JSON {@link FotoDTO} - La foto guardada con el atributo id
     * autogenerado.
     */
    @POST
    public FotoDTO createFoto(@PathParam("toursId") Long toursId, FotoDTO foto){
        return new FotoDTO(fotoLogic.createFoto(toursId, foto.toEntity()));
    }

    /**
     * Busca y devuelve todas las fotos que existen en un Tour.
     *
     * @param toursId El ID del Tour del cual se buscan las fotos
     * @return JSONArray {@link FotoDTO} - Las fotos encontradas en el
     * Tour. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<FotoDTO> getFotos(@PathParam("toursId") Long toursId) {
        return listEntity2DTO(fotoLogic.getFotos(toursId));
    }

    /**
     * Busca y devuelve la foto con el ID recibido en la URL, relativa a un
     * Tour.
     *
     * @param toursId El ID del Tour del cual se buscan las fotos
     * @param fotosId El ID de la foto que se busca
     * @return {@link FotoDTO} - La foto encontradas en el Tour.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la foto.
     */
    @GET
    @Path("{fotosId: \\d+}")
    public FotoDTO getFoto(@PathParam("toursId") Long toursId, @PathParam("fotosId") Long fotosId){
        FotoEntity entity = fotoLogic.getFoto(fotosId,toursId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO + toursId + FOTOS + fotosId + NOEXISTE, 404);
        }
        return new FotoDTO(entity);
    }

    /**
     * Actualiza una foto con la informacion que se recibe en el cuerpo de la
     * petición y se regresa el objeto actualizado.
     *
     * @param toursId El ID del Tour del cual se guarda la foto
     * @param fotosId El ID de la foto que se va a actualizar
     * @param foto {@link FotoDTO} - La foto que se desea guardar.
     * @return JSON {@link FotoDTO} - La foto actualizada.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la foto.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la foto.
     */
    @PUT
    @Path("{fotosId: \\d+}")
    public FotoDTO updateFoto(@PathParam("toursId") Long toursId, @PathParam("fotosId") Long fotosId, FotoDTO foto) throws BusinessLogicException {
        if (!fotosId.equals(foto.getId())) {
            throw new BusinessLogicException("Los ids de la foto no coinciden. " );
        }
        FotoEntity entity = fotoLogic.getFoto(fotosId,toursId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO + toursId + FOTOS + fotosId + NOEXISTE, 404);

        }
        return new FotoDTO(fotoLogic.updateFoto(toursId, foto.toEntity()));

    }

    /**
     * Borra la foto con el id asociado recibido en la URL.
     *
     * @param toursId El ID del Tour del cual se va a eliminar la foto.
     * @param fotosId El ID de la foto que se va a eliminar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la foto.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la foto.
     */
    @DELETE
    @Path("{fotosId: \\d+}")
    public void deleteFoto(@PathParam("toursId") Long toursId, @PathParam("fotosId") Long fotosId) throws BusinessLogicException {
        FotoEntity entity = fotoLogic.getFoto(fotosId,toursId);
        if (entity == null) {
            throw new WebApplicationException(RECURSO + toursId + FOTOS + fotosId + NOEXISTE, 404);
        }
        fotoLogic.deleteFoto(toursId, fotosId);
    }

    /**
     * Lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PrizeEntity a una lista de
     * objetos FotoDTO (json)
     *
     * @param entityList corresponde a la lista de fotos de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de fotos en forma DTO (json)
     */
    private List<FotoDTO> listEntity2DTO(List<FotoEntity> entityList) {
        List<FotoDTO> list = new ArrayList<>();
        for (FotoEntity entity : entityList) {
            list.add(new FotoDTO(entity));
        }
        return list;
    }

}
