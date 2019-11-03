/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Oscar Julian Castañeda G.
 */
@RunWith(Arquillian.class)
public class BlogLogicTest {


    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private BlogLogic blogLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<BlogEntity> data = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BlogEntity.class.getPackage())
                .addPackage(BlogLogic.class.getPackage())
                .addPackage(BlogPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from BlogEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            BlogEntity entity = factory.manufacturePojo(BlogEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Blog
     *
     * @throws co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException
     */
    @Test
    public void createBlogTest() throws BusinessLogicException {
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);
        BlogEntity result = blogLogic.createBlog(newEntity);
        Assert.assertNotNull(result);
        BlogEntity entity = em.find(BlogEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getTexto(), entity.getTexto());
        Assert.assertEquals(newEntity.getRutaImagen(), entity.getRutaImagen());
        Assert.assertEquals(newEntity.getRutaVideo(), entity.getRutaVideo());
        Assert.assertEquals(newEntity.getTitulo(), entity.getTitulo());
        Assert.assertEquals(newEntity.getCalificacionPromedio(), entity.getCalificacionPromedio(), 0.001);
    }


    @Test (expected = BusinessLogicException.class)
    public void createBlogContenidoNull() throws BusinessLogicException
    {
        BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
        blog.setTexto(null);
        blog.setRutaImagen(null);
        blog.setRutaVideo(null);
        BlogEntity result = blogLogic.createBlog(blog);  
    }

    @Test (expected = BusinessLogicException.class)
    public void createBlogContenidoVacio() throws BusinessLogicException
    {
        BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
        blog.setTexto("");
        blog.setRutaImagen("");
        blog.setRutaVideo("");
        BlogEntity result = blogLogic.createBlog(blog);  
    }
    @Test (expected = BusinessLogicException.class)
    public void createBlogTituloNull() throws BusinessLogicException
    {
        BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
        blog.setTitulo(null);
        BlogEntity result = blogLogic.createBlog(blog);  
    }
        @Test (expected = BusinessLogicException.class)
    public void createBlogTituloVacio() throws BusinessLogicException
    {
        BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
        blog.setTitulo("");
        BlogEntity result = blogLogic.createBlog(blog);  
    }
        /**
     * Prueba para consultar la lista de Blogs.
     */
    @Test
    public void getBlogsTest() {
        List<BlogEntity> list = blogLogic.getBlogs();
        Assert.assertEquals(data.size(), list.size());
        for (BlogEntity entity : list) {
            boolean found = false;
            for (BlogEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Blog.
     */
    @Test
    public void getBlogTest() {
        BlogEntity entity = data.get(0);
        BlogEntity resultEntity = blogLogic.getBlog(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(resultEntity.getTexto(), entity.getTexto());
        Assert.assertEquals(resultEntity.getRutaImagen(), entity.getRutaImagen());
        Assert.assertEquals(resultEntity.getRutaVideo(), entity.getRutaVideo());
        Assert.assertEquals(resultEntity.getCalificacionPromedio(), entity.getCalificacionPromedio(),0.001);
        Assert.assertEquals(resultEntity.getTitulo(), entity.getTitulo());
    }

    /**
     * Prueba para actualizar un Blog.
     */
    @Test
    public void updateBlogTest(){
        BlogEntity entity = data.get(0);
        BlogEntity pojoEntity = factory.manufacturePojo(BlogEntity.class);
        pojoEntity.setId(entity.getId());
        blogLogic.updateBlog(pojoEntity);
        BlogEntity resp = em.find(BlogEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getTexto(), resp.getTexto());
        Assert.assertEquals(pojoEntity.getRutaImagen(), resp.getRutaImagen());
        Assert.assertEquals(pojoEntity.getRutaVideo(), resp.getRutaVideo());
        Assert.assertEquals(pojoEntity.getCalificacionPromedio(), resp.getCalificacionPromedio(),0.001);
        Assert.assertEquals(pojoEntity.getTitulo(), resp.getTitulo());
    }

    /**
     * Prueba para eliminar un Blog.
     */
    @Test
    public void deleteBlogTest()  {
        BlogEntity entity = data.get(0);
        blogLogic.deleteBlog(entity.getId());
        BlogEntity deleted = em.find(BlogEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

