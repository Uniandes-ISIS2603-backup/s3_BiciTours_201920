/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.persistence;

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
 * @author Jhuliana Barrios
 */
@RunWith(Arquillian.class)
public class TourPersistenceTest {
         
    @Inject
    private TourPersistence tp;
    
    @PersistenceContext 
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<TourEntity> data = new ArrayList<TourEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(TourEntity.class)
                .addClass(TourPersistence.class)
                .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
                .addAsManifestResource("META-INF/beans.xml","beans.xml");
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
     * Inserta los datos iniciales 
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            TourEntity entity = factory.manufacturePojo(TourEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
       
     /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from TourEntity").executeUpdate();
    }
    
     /**
     * Prueba el funcionamiento del método findAll() de TourPersistence
     */
    @Test
    public void findAllTest() {
        List<TourEntity> list = tp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (TourEntity ent : list) {
            boolean found = false;
            for (TourEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para el métofo getTourTest() que retorna un tour si existe
     */
    @Test
    public void getTourTest() {
        TourEntity entity = data.get(0);
        TourEntity newEntity = tp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getNombre(),entity.getNombre());
        Assert.assertEquals(newEntity.getCosto(),entity.getCosto());
        Assert.assertEquals(newEntity.getLugar(),entity.getLugar());
        Assert.assertEquals(newEntity.getDescripcion(),entity.getDescripcion());
        Assert.assertEquals(newEntity.getDuracion(),entity.getDuracion());
        Assert.assertEquals(newEntity.getDificultad(),entity.getDificultad());
        Assert.assertEquals(newEntity.getFecha(),entity.getFecha());
        Assert.assertEquals(newEntity.getTerminado(),entity.getTerminado());
    }

    /**
     * Prueba el método create() de la clase TourPersistence
     */
    @Test
    public void createTourTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        TourEntity newTEntity = factory.manufacturePojo(TourEntity.class);
        TourEntity tEntity = tp.create(newTEntity);
        Assert.assertNotNull(tEntity);
        TourEntity entity = em.find(TourEntity.class, tEntity.getId());
        Assert.assertEquals(newTEntity.getNombre(),entity.getNombre());
        Assert.assertEquals(newTEntity.getCosto(),entity.getCosto());
        Assert.assertEquals(newTEntity.getLugar(),entity.getLugar());
        Assert.assertEquals(newTEntity.getDescripcion(),entity.getDescripcion());
        Assert.assertEquals(newTEntity.getDuracion(),entity.getDuracion());
        Assert.assertEquals(newTEntity.getDificultad(),entity.getDificultad());
        Assert.assertEquals(newTEntity.getFecha(),entity.getFecha());
        Assert.assertEquals(newTEntity.getTerminado(),entity.getTerminado());
    }
    
    
    /**
     * Prueba el método que elimina un tour.
     */
    @Test
    public void deleteTourTest() {
        TourEntity entity = data.get(0);
        tp.delete(entity.getId());
        TourEntity deleted = em.find(TourEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba que un tour haya sido actualizado
     */
    @Test
    public void updateTourTest() {
        TourEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        TourEntity newEntity = factory.manufacturePojo(TourEntity.class);

        newEntity.setId(entity.getId());

        tp.update(newEntity);

        TourEntity resp = em.find(TourEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(),resp.getNombre());
        Assert.assertEquals(newEntity.getCosto(),resp.getCosto());
        Assert.assertEquals(newEntity.getDuracion(),resp.getDuracion());
        Assert.assertEquals(newEntity.getFecha(),resp.getFecha());
        Assert.assertEquals(newEntity.getDescripcion(),resp.getDescripcion());
        Assert.assertEquals(newEntity.getDificultad(),resp.getDificultad());
        Assert.assertEquals(newEntity.getLugar(),resp.getLugar());
        Assert.assertEquals(newEntity.getTerminado(),resp.getTerminado());

    }

}

