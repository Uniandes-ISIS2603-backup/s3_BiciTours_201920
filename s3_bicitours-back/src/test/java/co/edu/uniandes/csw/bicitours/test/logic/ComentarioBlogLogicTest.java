/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.ComentarioBlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
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
 *
 * @author JuanRueda
 */
@RunWith(Arquillian.class)
public class ComentarioBlogLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ComentarioLogic comentarioLogic;
    @Inject
    private ComentarioBlogLogic comentarioBlogLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<BlogEntity> data = new ArrayList<BlogEntity>();

    private List<ComentarioEntity> comentariosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BlogEntity.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
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
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from BlogEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
            comentario.setCalificacion(2);
            comentario.setTexto("Hola");
            em.persist(comentario);
            comentariosData.add(comentario);
        }
        for (int i = 0; i < 3; i++) {
            BlogEntity entity = factory.manufacturePojo(BlogEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                comentariosData.get(i).setBlog(entity);
            }
        }
    }

    /**
     * Prueba para remplazar las instancias de Comentarios asociadas a una
     * instancia de Blog.
     */
    @Test
    public void replaceBlogTest() {
        ComentarioEntity entity = comentariosData.get(0);
        comentarioBlogLogic.replaceBlog(entity.getId(), data.get(1).getId());
        entity = comentarioLogic.getComentario(entity.getId());
        Assert.assertEquals(entity.getBlog(), data.get(1));
    }

    /**
     * Prueba para desasociar un Comentario existente de un Blog existente
     *
     */
    @Test
    public void removeComentariosTest() {
        comentarioBlogLogic.removeBlog(comentariosData.get(0).getId());
        ComentarioEntity response = comentarioLogic.getComentario(comentariosData.get(0).getId());
        Assert.assertNull(response.getBlog());
    }
}
