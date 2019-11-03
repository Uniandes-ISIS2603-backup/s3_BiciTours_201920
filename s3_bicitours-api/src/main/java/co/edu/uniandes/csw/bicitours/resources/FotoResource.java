/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.FotoDTO;
import co.edu.uniandes.csw.bicitours.ejb.FotoLogic;
import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Jhuliana Barrios
 */
@Path("fotos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class FotoResource {

    @Inject
    private FotoLogic fotoLogic;

    /**
     * Crea una nueva foto con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con una identificación generada
     * por la base de datos.
     *
     * @param t {@link FotoDTO} - La foto que se desea guardar.
     * @return JSON {@link FotoDTO} - La foto guardada con el atributo id
     * autogenerado.
     */
    @POST
    public FotoDTO createFoto(FotoDTO t) {
        FotoEntity fotoEntity = t.toEntity();
        FotoEntity nuevoFotoEntity = fotoLogic.createFoto(fotoEntity);

        return new FotoDTO(nuevoFotoEntity);
    }

    /**
     * Borra la foto con el id asociado recibido en la URL.
     *
     * @param fotosId Identificador de la foto que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra la foto a eliminar.
     */
    @DELETE
    @Path("{fotosId: \\d+}")
    public void deletoFoto(@PathParam("fotosId") long fotosId) {
        if (fotoLogic.getFoto(fotosId) == null) {
            throw new WebApplicationException("El recurso /fotos/" + fotosId + " no existe.", 404);
        }
        fotoLogic.deleteFoto(fotosId);
    }

    /**
     * Busca la foto con el id asociado recibido en la URL y lo devuelve.
     *
     * @param fotosId Identificador de la foto que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link FotoDTO} - La foto buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la foto.
     */
    @GET
    @Path("{fotosId: \\d+}")
    public FotoDTO getFoto(@PathParam("fotoId") long fotosId) {
        FotoEntity entidad = fotoLogic.getFoto(fotosId);

        if (entidad == null) {
            throw new WebApplicationException("El recurso /fotos/" + fotosId + " no existe.", 404);
        }

        return new FotoDTO(entidad);

    }

}
