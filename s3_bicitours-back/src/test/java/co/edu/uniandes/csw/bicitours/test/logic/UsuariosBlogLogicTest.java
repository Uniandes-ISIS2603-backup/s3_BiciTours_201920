/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.FavoritosBlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuariosBlogLogic;
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
public class UsuariosBlogLogicTest {
        
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private BlogLogic blogLogic;
    @Inject
    private UsuariosBlogLogic usuariosBlogLogic;

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
    public void addUsuarioTest() {
        UsuarioEntity entity = data.get(1);
        BlogEntity blogEntity = blogsData.get(0);
        UsuarioEntity response = usuariosBlogLogic.addUsuario(entity.getId(), blogEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getNombre(), response.getNombre());
        Assert.assertEquals(entity.getCodigo(), response.getCodigo());
        Assert.assertEquals(entity.getDeuda(), response.getDeuda());
        Assert.assertEquals(entity.isEsAdmin(), response.isEsAdmin());
        Assert.assertEquals(entity.isPago(), response.isPago());
    }

    @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> list = usuariosBlogLogic.getUsuarios(blogsData.get(0).getId());

        Assert.assertEquals(1, list.size());
    }

    @Test
    public void getusuarioTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(0);
        BlogEntity blogEntity = blogsData.get(0);
        UsuarioEntity response = usuariosBlogLogic.getUsuario(blogEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getNombre(), response.getNombre());
        Assert.assertEquals(entity.getCodigo(), response.getCodigo());
        Assert.assertEquals(entity.getDeuda(), response.getDeuda());
        Assert.assertEquals(entity.isEsAdmin(), response.isEsAdmin());
        Assert.assertEquals(entity.isPago(), response.isPago());
    }

    @Test(expected = BusinessLogicException.class)
    public void getUsuarioNoAsociadoTest() throws BusinessLogicException {
        UsuarioEntity entity = data.get(1);
        BlogEntity blogEntity = blogsData.get(0);
        usuariosBlogLogic.getUsuario(entity.getId(), blogEntity.getId());
    }

    /**@Test
    public void removeUsuarioTest() throws BusinessLogicException{
        usuariosBlogLogic.removeUsuario(blogsData.get(0).getId(),data.get(0).getId());
        Assert.assertEquals(0,usuariosBlogLogic.getUsuarios(data.get(0).getId()).size());
    }*/
    @Test(expected = BusinessLogicException.class)
    public void removeUsuarioNoAsociadoTest() throws BusinessLogicException{
        usuariosBlogLogic.removeUsuario(data.get(0).getId(), blogsData.get(1).getId());
    }       
}
