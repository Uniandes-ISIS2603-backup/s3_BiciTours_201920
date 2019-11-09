/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.FavoritosBlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
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
 * @author Oscar Julian Castañeda G.
 */
@RunWith(Arquillian.class)
public class FavoritosBlogLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private FavoritosBlogLogic favoritosBlogLogic;

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
            blogs.setUsuarios(new ArrayList());
            blogsData.add(blogs);

        }
        for (int i = 0; i < 3; i++) {
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            entity.setFavoritos(new ArrayList());
            data.add(entity);
        }
        blogsData.get(0).getUsuarios().add(data.get(0));
        data.get(0).getFavoritos().add(blogsData.get(0));
    }

    @Test
    public void addFavoritoTest() {
        UsuarioEntity entity = data.get(0);
        BlogEntity blogEntity = blogsData.get(1);
        BlogEntity response = favoritosBlogLogic.addFavorito(blogEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(blogEntity.getId(), response.getId());
        Assert.assertEquals(blogEntity.getTexto(), response.getTexto());
        Assert.assertEquals(blogEntity.getRutaImagen(), response.getRutaImagen());
        Assert.assertEquals(blogEntity.getRutaVideo(), response.getRutaVideo());
        Assert.assertEquals(blogEntity.getTitulo(), response.getTitulo());
        Assert.assertEquals(blogEntity.getCalificacionPromedio(), response.getCalificacionPromedio(), 0.001);
    }

    @Test
    public void getFavoritosTest() {
        List<BlogEntity> list = favoritosBlogLogic.getFavoritos(data.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getFavoritoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        BlogEntity blogEntity = blogsData.get(0);
        BlogEntity response = favoritosBlogLogic.getFavorito(entity.getId(), blogEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(blogEntity.getId(), response.getId());
        Assert.assertEquals(blogEntity.getTexto(), response.getTexto());
        Assert.assertEquals(blogEntity.getRutaImagen(), response.getRutaImagen());
        Assert.assertEquals(blogEntity.getRutaVideo(), response.getRutaVideo());
        Assert.assertEquals(blogEntity.getTitulo(), response.getTitulo());
        Assert.assertEquals(blogEntity.getCalificacionPromedio(), response.getCalificacionPromedio(), 0.001);
    }

    @Test(expected = BusinessLogicException.class)
    public void getFavoritoNoAsociadoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        BlogEntity blogEntity = blogsData.get(1);
        favoritosBlogLogic.getFavorito(entity.getId(), blogEntity.getId());
    }

    @Test
    public void replaceFavoritosTest() {
        UsuarioEntity entity = data.get(0);
        List<BlogEntity> list = blogsData.subList(1, 3);
        favoritosBlogLogic.replaceFavoritos(entity.getId(), list);

        entity = usuarioLogic.getUsuario(entity.getId());
        Assert.assertFalse(entity.getFavoritos().contains(blogsData.get(0)));
        Assert.assertTrue(entity.getFavoritos().contains(blogsData.get(1)));
        Assert.assertTrue(entity.getFavoritos().contains(blogsData.get(2)));
    }

    @Test
    public void removeFavoritoTest() throws BusinessLogicException {
        favoritosBlogLogic.removeFavorito(data.get(0).getId(), blogsData.get(0).getId());
        Assert.assertEquals(0, favoritosBlogLogic.getFavoritos(blogsData.get(0).getId()).size());
    }

    @Test(expected = BusinessLogicException.class)
    public void removeFavoritoNoAsociadoTest() throws BusinessLogicException {
        favoritosBlogLogic.removeFavorito(blogsData.get(0).getId(), data.get(1).getId());
    }
}
