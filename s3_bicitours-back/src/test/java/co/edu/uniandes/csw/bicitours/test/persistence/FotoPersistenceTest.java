/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.persistence;

import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.persistence.FotoPersistence;
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
public class FotoPersistenceTest {

    @Inject
    private FotoPersistence tp;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<FotoEntity> data = new ArrayList<FotoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FotoEntity.class.getPackage())
                .addPackage(FotoPersistence.class.getPackage())
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
     * Inserta los datos iniciales
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            FotoEntity entity = factory.manufacturePojo(FotoEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from FotoEntity").executeUpdate();
    }

    /**
     * Prueba el funcionamiento del método findAll() de FotoPersistence
     */
    @Test
    public void findAllTest() {
        List<FotoEntity> list = tp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (FotoEntity ent : list) {
            boolean found = false;
            for (FotoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para el métofo getFototTest() que retorna una foto si existe
     */
    @Test
    public void getFotoTest() {
        FotoEntity entity = data.get(0);
        FotoEntity newEntity = tp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(newEntity.getRuta(), entity.getRuta());
    }

    /**
     * Prueba el método create() de la clase FotoPersistence
     */
    @Test
    public void createFotoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        FotoEntity newFEntity = factory.manufacturePojo(FotoEntity.class);
        FotoEntity fEntity = tp.create(newFEntity);
        Assert.assertNotNull(fEntity);
        FotoEntity entity = em.find(FotoEntity.class, fEntity.getId());
        Assert.assertEquals(newFEntity.getRuta(), entity.getRuta());
    }

    /**
     * Prueba el método que elimina una foto.
     */
    @Test
    public void deleteFotoTest() {
        FotoEntity entity = data.get(0);
        tp.delete(entity.getId());
        FotoEntity deleted = em.find(FotoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba que una foto haya sido actualizada
     */
    @Test
    public void updateFotoTest() {
        FotoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);

        newEntity.setId(entity.getId());

        tp.update(newEntity);

        FotoEntity resp = em.find(FotoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getRuta(), resp.getRuta());

    }

}
