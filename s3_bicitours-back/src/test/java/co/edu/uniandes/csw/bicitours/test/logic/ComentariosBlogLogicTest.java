/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.ComentariosBlogLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
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
public class ComentariosBlogLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private BlogLogic blogLogic;
    @Inject
    private ComentariosBlogLogic comentariosBlogLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<BlogEntity> data = new ArrayList<BlogEntity>();

    private List<ComentarioEntity> comentariosData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(BlogEntity.class.getPackage())
                .addPackage(BlogLogic.class.getPackage())
                .addPackage(BlogPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

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

    private void clearData() {
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from BlogEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentarios = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(comentarios);
            comentariosData.add(comentarios);
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

    @Test
    public void addComentariosTest() {
        BlogEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(1);
        ComentarioEntity response = comentariosBlogLogic.addComentario(comentarioEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(comentarioEntity.getId(), response.getId());
        Assert.assertEquals(comentarioEntity.getCalificacion(), response.getCalificacion());
        Assert.assertEquals(comentarioEntity.getTexto(), response.getTexto());
    }

    @Test
    public void getComentariosTest() {
        List<ComentarioEntity> list = comentariosBlogLogic.getComentarios(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getComentarioTest() throws BusinessLogicException {
        BlogEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(0);
        ComentarioEntity response = comentariosBlogLogic.getComentario(comentarioEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(comentarioEntity.getId(), response.getId());
        Assert.assertEquals(comentarioEntity.getCalificacion(), response.getCalificacion());
        Assert.assertEquals(comentarioEntity.getTexto(), response.getTexto());
    }

    @Test(expected = BusinessLogicException.class)
    public void getComentarioNoAsociadoTest() throws BusinessLogicException {
        BlogEntity entity = data.get(0);
        ComentarioEntity comentarioEntity = comentariosData.get(1);
        comentariosBlogLogic.getComentario(entity.getId(), comentarioEntity.getId());
    }

    @Test
    public void replaceComentariosTest() {
        BlogEntity entity = data.get(0);
        List<ComentarioEntity> list = comentariosData.subList(1, 3);
        comentariosBlogLogic.replaceComentarios(entity.getId(), list);

        entity = blogLogic.getBlog(entity.getId());
        Assert.assertFalse(entity.getComentarios().contains(comentariosData.get(0)));
        Assert.assertTrue(entity.getComentarios().contains(comentariosData.get(1)));
        Assert.assertTrue(entity.getComentarios().contains(comentariosData.get(2)));
    }
    
    @Test
    public void removeComentarioTest() throws BusinessLogicException{
        comentariosBlogLogic.removeComentario(comentariosData.get(0).getId(), data.get(0).getId());
        Assert.assertEquals(0, comentariosBlogLogic.getComentarios(data.get(0).getId()).size());
    }
    @Test(expected = BusinessLogicException.class)
    public void removeComentarioNoAsociadoTest() throws BusinessLogicException{
        comentariosBlogLogic.removeComentario(comentariosData.get(0).getId(), data.get(1).getId());
    }
    @Test
    public void getBlogTest() {
        BlogEntity blog = comentariosBlogLogic.getBlog(comentariosData.get(0).getId());

        Assert.assertEquals(data.get(0), blog);
    }
    @Test
        public void removeBlogTest() {
        comentariosBlogLogic.removeBlog(comentariosData.get(0).getId());
        Assert.assertNull(comentariosBlogLogic.getBlog(comentariosData.get(0).getId()));
    }
    @Test
        public void replaceBlogTest() {
        comentariosBlogLogic.replaceBlog(comentariosData.get(0).getId(),data.get(1).getId());
        Assert.assertEquals(comentariosBlogLogic.getBlog(comentariosData.get(0).getId()), data.get(1));
    }        
}
