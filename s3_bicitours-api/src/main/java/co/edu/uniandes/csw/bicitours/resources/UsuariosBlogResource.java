/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.resources;

import co.edu.uniandes.csw.bicitours.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuariosBlogLogic;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuariosBlogResource {
    @Inject
    private UsuariosBlogLogic usuariosBlogLogic;

    @Inject
    private UsuarioLogic usuarioLogic;

    /**@POST
    @Path("{usuariosId: \\d+}")
    public BlogDetailDTO addUsuario(@PathParam("blogsId") Long blogsId, @PathParam("usuariosId") Long usuariosId) {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuariosBlogLogic.addUsuario(blogsId, usuariosId));
        return detailDTO;
    }

    @GET
    public List<UsuarioDetailDTO> getUsuarios(@PathParam("blogsId") Long blogsId) {
        List<UsuarioDetailDTO> lista = blogsListEntity2DTO(usuariosBlogLogic.getBlogs(blogsId));
        return lista;
    }

    @GET
    @Path("{usuariosId: \\d+}")
    public BlogDetailDTO getUsuario(@PathParam("blogsId") Long blogsId, @PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /blogs/" + usuariosId + " no existe.", 404);
        }
        UsuarioDetailDTO detailDTO = new UsuarioDetailDTO(usuariosBlogLogic.getBlog(blogsId, usuariosId));
        return detailDTO;
    }

    @DELETE
    @Path("{usuariosId: \\d+}")
    public void removeUsuario(@PathParam("blogsId") Long blogsId, @PathParam("usuariosId") Long usuariosId) throws BusinessLogicException {
        if (usuarioLogic.getUsuario(usuariosId) == null) {
            throw new WebApplicationException("El recurso /usuarios/" + usuariosId + " no existe.", 404);
        }
        usuariosBlogLogic.removeUsuario(blogsId, usuariosId);
    }

    private List<UsuarioDetailDTO> usuariosListEntity2DTO(List<UsuarioEntity> entityList) {
        List<UsuarioDetailDTO> list = new ArrayList<>();
        for (UsuarioEntity entity : entityList) {
            list.add(new UsuarioDetailDTO(entity));
        }
        return list;
    }

    private List<UsuarioEntity> usuariosListDTO2Entity(List<UsuarioDetailDTO> dtos) {
        List<UsuarioEntity> list = new ArrayList<>();
        for (UsuarioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    } */    
}
