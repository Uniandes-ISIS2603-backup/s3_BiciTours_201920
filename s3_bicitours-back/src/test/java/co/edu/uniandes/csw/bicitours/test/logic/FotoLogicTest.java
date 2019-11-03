/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.FotoLogic;
import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
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
 * @author oj.castaneda
 */
@RunWith(Arquillian.class)
public class FotoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private FotoLogic fotoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<FotoEntity> data = new ArrayList<>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FotoEntity.class.getPackage())
                .addPackage(FotoLogic.class.getPackage())
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
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from FotoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            FotoEntity entity = factory.manufacturePojo(FotoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createFotoTest() throws BusinessLogicException {
        FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
        FotoEntity result = fotoLogic.createFoto(newEntity);
        Assert.assertNotNull(result);
        FotoEntity entity = em.find(FotoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getRuta(), entity.getRuta());
    }

    @Test
    public void getFotosTest() {
        List<FotoEntity> list = fotoLogic.getFotos();
        Assert.assertEquals(data.size(), list.size());
        for (FotoEntity entity : list) {
            boolean found = false;
            for (FotoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void getFotoTest() {
        FotoEntity entity = data.get(0);
        FotoEntity resultEntity = fotoLogic.getFoto(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getRuta(), resultEntity.getRuta());
    }

    @Test
    public void updateFotoTest() {
        FotoEntity entity = data.get(0);
        FotoEntity pojoEntity = factory.manufacturePojo(FotoEntity.class);
        pojoEntity.setId(entity.getId());
        fotoLogic.updateFoto(pojoEntity);
        FotoEntity resp = em.find(FotoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getRuta(), resp.getRuta());
    }

    @Test
    public void deleteFotoTest() {
        FotoEntity entity = data.get(0);
        fotoLogic.deleteFoto(entity.getId());
        FotoEntity deleted = em.find(FotoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
