/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioMisBlogsLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
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
 * @author Juan Sebastián González Rojas
 */
@RunWith(Arquillian.class)
public class UsuarioMisBlogsLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;

    @Inject
    private UsuarioMisBlogsLogic usuarioMisBlogsLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();

    private List<BlogEntity> blogsData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(UsuarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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
        em.createQuery("delete from BlogEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            BlogEntity blogs = factory.manufacturePojo(BlogEntity.class);
            em.persist(blogs);
            blogsData.add(blogs);
        }
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                blogsData.get(i).setCreador(entity);
            }
        }
    }

    @Test
    public void addBlogsTest() {
        UsuarioEntity entity = data.get(0);
        BlogEntity blogEntity = blogsData.get(1);
        BlogEntity response = usuarioMisBlogsLogic.addBlog(blogEntity.getId(), entity.getId());
        Assert.assertNotNull(response);
        Assert.assertEquals(blogEntity.getId(), response.getId());
        Assert.assertEquals(blogEntity.getTexto(), response.getTexto());
        Assert.assertEquals(blogEntity.getRutaImagen(), response.getRutaImagen());
        Assert.assertEquals(blogEntity.getRutaVideo(), response.getRutaVideo());
        Assert.assertEquals(blogEntity.getTitulo(), response.getTitulo());
        Assert.assertEquals(blogEntity.getCalificacionPromedio(), response.getCalificacionPromedio(), 0.001);
    }

    @Test
    public void getBlogsTest() {
        List<BlogEntity> list = usuarioMisBlogsLogic.getBlogs(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getBlogTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        BlogEntity blogEntity = blogsData.get(0);
        BlogEntity response = usuarioMisBlogsLogic.getBlog(entity.getId(), blogEntity.getId());
        Assert.assertNotNull(response);
        Assert.assertEquals(blogEntity.getId(), response.getId());
        Assert.assertEquals(blogEntity.getTexto(), response.getTexto());
        Assert.assertEquals(blogEntity.getRutaImagen(), response.getRutaImagen());
        Assert.assertEquals(blogEntity.getRutaVideo(), response.getRutaVideo());
        Assert.assertEquals(blogEntity.getTitulo(), response.getTitulo());
        Assert.assertEquals(blogEntity.getCalificacionPromedio(), response.getCalificacionPromedio(), 0.001);
    }

    @Test(expected = BusinessLogicException.class)
    public void getBlogNoAsociadoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        BlogEntity blogEntity = blogsData.get(1);
        usuarioMisBlogsLogic.getBlog(entity.getId(), blogEntity.getId());
    }

    @Test
    public void setBlogsTest() {
        UsuarioEntity entity = data.get(0);
        List<BlogEntity> list = blogsData.subList(1, 3);
        usuarioMisBlogsLogic.replaceBlogs(entity.getId(), list);
        entity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertFalse(entity.getMisBlogs().contains(blogsData.get(0)));
        Assert.assertTrue(entity.getMisBlogs().contains(blogsData.get(1)));
        Assert.assertTrue(entity.getMisBlogs().contains(blogsData.get(2)));
    }

    @Test
    public void removeBlogTest() throws BusinessLogicException {
        usuarioMisBlogsLogic.removeBlog(data.get(0).getId(), blogsData.get(0).getId());
        Assert.assertEquals(0, usuarioMisBlogsLogic.getBlogs(blogsData.get(0).getId()).size());
    }

    @Test(expected = BusinessLogicException.class)
    public void removeBlogNoAsociadoTest() throws BusinessLogicException {
        usuarioMisBlogsLogic.removeBlog(blogsData.get(0).getId(), data.get(1).getId());
    }
}
