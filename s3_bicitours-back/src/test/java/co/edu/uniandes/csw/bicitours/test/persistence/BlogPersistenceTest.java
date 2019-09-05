/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.persistence;

import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.persistence.BlogPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de persistencia de Blogs
 *
 * @author Oscar Julian Castañeda G.
 */
@RunWith(Arquillian.class)
public class BlogPersistenceTest {

    @Inject
    private BlogPersistence bp;
    @PersistenceContext
    private EntityManager em;
    @Inject
    UserTransaction utx;
    private List<BlogEntity> data = new ArrayList<BlogEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BlogEntity.class)
                .addClass(BlogPersistence.class)
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
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            BlogEntity entity = factory.manufacturePojo(BlogEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Blog.
     */
    @Test
    public void createTestBlog() {
        PodamFactory factory = new PodamFactoryImpl();
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);
        BlogEntity result = bp.create(newEntity);
        Assert.assertNotNull(result);
        BlogEntity entity = em.find(BlogEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getTexto(), entity.getTexto());
        Assert.assertEquals(newEntity.getRutaImagen(), entity.getRutaImagen());
        Assert.assertEquals(newEntity.getRutaVideo(), entity.getRutaVideo());
        Assert.assertEquals(newEntity.getCalificacionPromedio(), entity.getCalificacionPromedio(),0.001);
        Assert.assertEquals(newEntity.getTitulo(), entity.getTitulo());
    }

    /**
     * Prueba para consultar la lista de Blogs.
     */
    @Test
    public void getBlogsTest() {
        List<BlogEntity> list = bp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (BlogEntity ent : list) {
            boolean found = false;
            for (BlogEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
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
        BlogEntity newEntity = bp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getTexto(), entity.getTexto());
        Assert.assertEquals(newEntity.getRutaImagen(), entity.getRutaImagen());
        Assert.assertEquals(newEntity.getRutaVideo(), entity.getRutaVideo());
        Assert.assertEquals(newEntity.getCalificacionPromedio(), entity.getCalificacionPromedio(),0.001);
        Assert.assertEquals(newEntity.getTitulo(), entity.getTitulo());
    }

    /**
     * Prueba para eliminar un Blog.
     */
    @Test
    public void deleteBlogTest() {
        BlogEntity entity = data.get(0);
        bp.delete(entity.getId());
        BlogEntity deleted = em.find(BlogEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Blog.
     */
    @Test
    public void updateBlogTest() {
        BlogEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);

        newEntity.setId(entity.getId());

        bp.update(newEntity);

        BlogEntity resp = em.find(BlogEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getTexto(), resp.getTexto());
        Assert.assertEquals(newEntity.getRutaImagen(), resp.getRutaImagen());
        Assert.assertEquals(newEntity.getRutaVideo(), resp.getRutaVideo());
        Assert.assertEquals(newEntity.getCalificacionPromedio(), resp.getCalificacionPromedio(),0.001);
        Assert.assertEquals(newEntity.getTitulo(), resp.getTitulo());
    }

}
