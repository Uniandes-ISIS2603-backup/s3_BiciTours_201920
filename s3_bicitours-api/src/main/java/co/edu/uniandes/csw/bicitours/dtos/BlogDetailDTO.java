/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
public class BlogDetailDTO extends BlogDTO implements Serializable{
    
    private List<UsuarioDTO> usuarios;
    private List<ComentarioDTO> comentarios;
    public BlogDetailDTO() {
        super();
    }

    public BlogDetailDTO(BlogEntity blogEntity) {
        super(blogEntity);
        if (blogEntity.getUsuarios() != null) {
            usuarios = new ArrayList<>();
            for (UsuarioEntity entityUsuario : blogEntity.getUsuarios()) {
                usuarios.add(new UsuarioDTO(entityUsuario));
            }
        }
        if (blogEntity.getComentarios() != null) {
            comentarios = new ArrayList<>();
            for (ComentarioEntity entityComentario : blogEntity.getComentarios()) {
                comentarios.add(new ComentarioDTO(entityComentario));
            }
        }        
    }

    @Override
    public BlogEntity toEntity() {
        BlogEntity blogEntity = super.toEntity();
        if (usuarios != null) {
            List<UsuarioEntity> usuariosEntity = new ArrayList<>();
            for (UsuarioDTO dtoUsuario : usuarios) {
                usuariosEntity.add(dtoUsuario.toEntity());
            }
            blogEntity.setUsuarios(usuariosEntity);
        }
        if (comentarios != null) {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for (ComentarioDTO dtoComentario : comentarios) {
                comentariosEntity.add(dtoComentario.toEntity());
            }
            blogEntity.setComentarios(comentariosEntity);
        }        
        return blogEntity;
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * @return the comentarios
     */
    public List<ComentarioDTO> getComentarios() {
        return comentarios;
    }

    /**
     * @param comentarios the comentarios to set
     */
    public void setComentarios(List<ComentarioDTO> comentarios) {
        this.comentarios = comentarios;
    }
}
