/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.dtos;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;

/**
 *
 * @author Estudiante
 */
public class BlogDetailDTO extends BlogDTO{
    
    public BlogDetailDTO() {
        super();
    }

    public BlogDetailDTO(BlogEntity blogEntity) {
        super(blogEntity);
    }

    @Override
    public BlogEntity toEntity() {
        BlogEntity blogEntity = super.toEntity();
        return blogEntity;
    }
}
