/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.BlogLogic;
import co.edu.uniandes.csw.bicitours.ejb.SiguienteBlogLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
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
public class SiguienteBlogLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SiguienteBlogLogic siguienteBlogLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<BlogEntity> data = new ArrayList<BlogEntity>();

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
        em.createQuery("delete from BlogEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 4; i++) {
            BlogEntity blogs = factory.manufacturePojo(BlogEntity.class);
            em.persist(blogs);
            data.add(blogs);
        }
        data.get(1).setSiguiente(data.get(2));
    }

    @Test
    public void removeSiguienteTest() {
        siguienteBlogLogic.removeSiguiente(data.get(1).getId());
        Assert.assertNull(siguienteBlogLogic.getSiguiente(data.get(1).getId()));
    }

    @Test
    public void getSiguienteTest() {
        BlogEntity siguiente = siguienteBlogLogic.getSiguiente(data.get(1).getId());

        Assert.assertEquals(data.get(2), siguiente);
    }

    @Test
    public void replaceSiguienteTest() {
        siguienteBlogLogic.replaceSiguiente(data.get(1).getId(), data.get(3).getId());
        Assert.assertEquals(siguienteBlogLogic.getSiguiente(data.get(1).getId()), data.get(3));
    }
}
