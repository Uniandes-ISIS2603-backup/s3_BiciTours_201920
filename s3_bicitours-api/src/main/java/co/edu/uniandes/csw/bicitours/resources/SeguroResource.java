/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;


import co.edu.uniandes.csw.bicitours.dtos.SeguroDTO;
import co.edu.uniandes.csw.bicitours.ejb.SeguroLogic;
import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author Estudiante
 */
@Path("cSeguros")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped

public class SeguroResource {
    
    @Inject
    private SeguroLogic logica;

    @POST
    public SeguroDTO createSeguro(SeguroDTO seguro) throws BusinessLogicException 
    {
       
        return null;
    }

    @GET
    public List<SeguroDTO> getSeguros() {
        
        return null;
    }

    
  
    @PUT
    @Path("{seguroId: \\d+}")
    public SeguroDTO updateSeguro(@PathParam("seguroId") Long seguroId, SeguroDTO seguros) throws BusinessLogicException 
    {
        
        return null;
    }

    @DELETE
    @Path("{seguroId: \\d+}")
    public void deleteSeguro(@PathParam("seguroId") Long seguroId) throws BusinessLogicException 
    {
        
    }
}
