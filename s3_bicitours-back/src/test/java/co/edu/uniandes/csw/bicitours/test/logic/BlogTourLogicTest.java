/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.BlogTourLogic;
import co.edu.uniandes.csw.bicitours.ejb.TourLogic;
import co.edu.uniandes.csw.bicitours.entities.BlogEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
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
public class BlogTourLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TourLogic tourLogic;
    @Inject
    private BlogTourLogic blogTourLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<TourEntity> data = new ArrayList<TourEntity>();

    private List<BlogEntity> blogsData = new ArrayList();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TourEntity.class.getPackage())
                .addPackage(TourLogic.class.getPackage())
                .addPackage(TourPersistence.class.getPackage())
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
        em.createQuery("delete from TourEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            BlogEntity blogs = factory.manufacturePojo(BlogEntity.class);
            em.persist(blogs);
            blogsData.add(blogs);
        }
        for (int i = 0; i < 3; i++) {
            TourEntity entity = factory.manufacturePojo(TourEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
               blogsData.get(i).setTour(entity);
            }
        }
    }
    @Test
    public void getTourTest() {
        TourEntity tour = blogTourLogic.getTour(blogsData.get(0).getId());

        Assert.assertEquals(data.get(0), tour);
    }
    @Test
        public void removeTourTest() {
        blogTourLogic.removeTour(blogsData.get(0).getId());
        Assert.assertNull(blogTourLogic.getTour(blogsData.get(0).getId()));
    }
    @Test
        public void replaceTourTest() {
        blogTourLogic.replaceTour(blogsData.get(0).getId(),data.get(1).getId());
        Assert.assertEquals(blogTourLogic.getTour(blogsData.get(0).getId()), data.get(1));
    }        
}
