/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.ejb;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@Stateless
public class BlogLogic {

    @Inject
    private BlogPersistence persistence;

    /**
     * Guardar un nuevo blog
     *
     * @param blog La entidad de tipo blog del nuevo blog a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException Si el titulo es inválido o el contenido es
     * invalido.
     */
    public BlogEntity createBlog(BlogEntity blog) throws BusinessLogicException {
        if (blog.getRutaImagen() == null && blog.getRutaVideo() == null && blog.getTexto() == null) {
            throw new BusinessLogicException("El blog no puede tener contenido nulo");
        } else if (blog.getRutaImagen().equals("") && blog.getRutaVideo().equals("") && blog.getTexto().equals("")) {
            throw new BusinessLogicException("El blog no puede tener contenido vacio");
        } else if (blog.getTitulo() == null || blog.getTitulo().equals("")) {
            throw new BusinessLogicException("El blog debe tener titulo");
        }

        return persistence.create(blog);
    }

    /**
     * Devuelve todos los bloigs que hay en la base de datos.
     *
     * @return Lista de entidades de tipo blog.
     */
    public List<BlogEntity> getBlogs() {

        return persistence.findAll();
    }

    /**
     * Busca un blog por ID
     *
     * @param blogsId El id del blog a buscar
     * @return El blog encontrado, null si no lo encuentra.
     */
    public BlogEntity getBlog(Long blogsId) {

        return persistence.find(blogsId);
    }

    /**
     * Actualizar un blog por ID
     *
     * @param blogEntity La entidad del blog con los cambios deseados
     * @return La entidad del blog luego de actualizarla
     */
    public BlogEntity updateBlog(BlogEntity blogEntity) {

        return persistence.update(blogEntity);
    }

    /**
     * Eliminar un blog por ID
     *
     * @param blogsId El ID del blog a eliminar
     *
     */
    public void deleteBlog(Long blogsId) {
        persistence.delete(blogsId);
    }
}
