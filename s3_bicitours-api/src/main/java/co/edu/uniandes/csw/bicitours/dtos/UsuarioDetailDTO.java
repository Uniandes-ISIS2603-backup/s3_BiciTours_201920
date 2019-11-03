/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Juan Sebastián González Rojas
 */
//clase para el manejo de relaciones con cardinalidad múltiple en modelo DTO
public class UsuarioDetailDTO extends UsuarioDTO implements Serializable {
    // relación  cero o muchos comentarios 

    private List<ComentarioDTO> comentarios;

    // relación  cero o muchos seguros 
    private List<SeguroDTO> seguros;

    // relación  cero o muchos Tours
    private List<TourDTO> visitados;

    // relación  cero o muchos Blog 
    private List<BlogDTO> favoritos;

    // relación  cero o muchos Blog
    private List<BlogDTO> misBlogs;

    /**
     * Constructor por defecto
     */
    public UsuarioDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO incluyendo atributos List
     *
     * @param usuarioEntity La entidad de la editorial para transformar a DTO.
     */
    public UsuarioDetailDTO(UsuarioEntity usuarioEntity) {
        super(usuarioEntity);
        //Revisión de listas asociadas para transformarlas a DTOs
        if (usuarioEntity.getComentarios() != null) {
            comentarios = new ArrayList<>();
            for (ComentarioEntity comentarioActual : usuarioEntity.getComentarios()) {
                comentarios.add(new ComentarioDTO(comentarioActual));
            }
        }
        if (usuarioEntity.getFavoritos() != null) {
            favoritos = new ArrayList<>();
            for (BlogEntity blogActual : usuarioEntity.getFavoritos()) {
                favoritos.add(new BlogDTO(blogActual));
            }
        }
        if (usuarioEntity.getMisBlogs() != null) {
            misBlogs = new ArrayList<>();
            for (BlogEntity blogActual : usuarioEntity.getMisBlogs()) {
                misBlogs.add(new BlogDTO(blogActual));
            }
        }
        if (usuarioEntity.getVisitados() != null) {
            visitados = new ArrayList<>();
            for (TourEntity tourActual : usuarioEntity.getVisitados()) {
                visitados.add(new TourDTO(tourActual));
            }
        }
        if (usuarioEntity.getSeguros() != null) {
            seguros = new ArrayList<>();
            for (SeguroEntity seguroActual : usuarioEntity.getSeguros()) {
                seguros.add(new SeguroDTO(seguroActual));
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO del usuario para transformar a Entity
     */
    @Override
    public UsuarioEntity toEntity() {
        UsuarioEntity usuarioEntity = super.toEntity();
        //Revisión de listas asociadas para transformarlas a entities
        if (comentarios != null) {
            List<ComentarioEntity> comentariosEntity = new ArrayList<>();
            for (ComentarioDTO comentarioDTOActual : getComentarios()) {
                comentariosEntity.add(comentarioDTOActual.toEntity());
            }
            usuarioEntity.setComentarios(comentariosEntity);
        }
        if (favoritos != null) {
            List<BlogEntity> blogsEntity = new ArrayList<>();
            for (BlogDTO blogDTOActual : getFavoritos()) {
                blogsEntity.add(blogDTOActual.toEntity());
            }
            usuarioEntity.setFavoritos(blogsEntity);
        }
        if (usuarioEntity.getMisBlogs() != null) {
            List<BlogEntity> misBlogsEntity = new ArrayList<>();
            for (BlogDTO blogDTOActual : getMisBlogs()) {
                misBlogsEntity.add(blogDTOActual.toEntity());
            }
            usuarioEntity.setMisBlogs(misBlogsEntity);
        }
        if (usuarioEntity.getVisitados() != null) {
            List<TourEntity> visitadosEntity = new ArrayList<>();
            for (TourDTO tourDTOActual : getVisitados()) {
                visitadosEntity.add(tourDTOActual.toEntity());
            }
            usuarioEntity.setVisitados(visitadosEntity);
        }
        if (usuarioEntity.getSeguros() != null) {
            List<SeguroEntity> segurosEntity = new ArrayList<>();
            for (SeguroDTO seguroDTOActual : getSeguros()) {
                segurosEntity.add(seguroDTOActual.toEntity());
            }
            usuarioEntity.setSeguros(segurosEntity);
        }

        return usuarioEntity;
    }

    /////////////////////////MÉTODOS DTOS////////////////////////////////////
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

    /**
     * @return the seguros
     */
    public List<SeguroDTO> getSeguros() {
        return seguros;
    }

    /**
     * @param seguros the seguros to set
     */
    public void setSeguros(List<SeguroDTO> seguros) {
        this.seguros = seguros;
    }

    /**
     * @return the visitados
     */
    public List<TourDTO> getVisitados() {
        return visitados;
    }

    /**
     * @param visitados the visitados to set
     */
    public void setVisitados(List<TourDTO> visitados) {
        this.visitados = visitados;
    }

    /**
     * @return the favoritos
     */
    public List<BlogDTO> getFavoritos() {
        return favoritos;
    }

    /**
     * @param favoritos the favoritos to set
     */
    public void setFavoritos(List<BlogDTO> favoritos) {
        this.favoritos = favoritos;
    }

    /**
     * @return the misBlogs
     */
    public List<BlogDTO> getMisBlogs() {
        return misBlogs;
    }

    /**
     * @param misBlogs the misBlogs to set
     */
    public void setMisBlogs(List<BlogDTO> misBlogs) {
        this.misBlogs = misBlogs;
    }

}
