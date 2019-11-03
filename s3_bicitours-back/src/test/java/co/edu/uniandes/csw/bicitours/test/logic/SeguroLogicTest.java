/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.SeguroLogic;
import co.edu.uniandes.csw.bicitours.ejb.SeguroLogic;
import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.SeguroPersistence;
import co.edu.uniandes.csw.bicitours.persistence.SeguroPersistence;
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
 * @author Maria Clara Noguera Echeverri
 */
@RunWith(Arquillian.class)
public class SeguroLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SeguroLogic seguroLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<SeguroEntity> data = new ArrayList<SeguroEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SeguroEntity.class.getPackage())
                .addPackage(SeguroLogic.class.getPackage())
                .addPackage(SeguroPersistence.class.getPackage())
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
        em.createQuery("delete from SeguroEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            SeguroEntity entity = factory.manufacturePojo(SeguroEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Seguro
     *
     * @throws co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException
     */
    @Test
    public void createSeguroTest() throws BusinessLogicException {
        SeguroEntity newEntity = factory.manufacturePojo(SeguroEntity.class);
        SeguroEntity result = seguroLogic.createSeguro(newEntity);
        Assert.assertNotNull(result);
        SeguroEntity entity = em.find(SeguroEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCaracteristicas(), entity.getCaracteristicas());
        Assert.assertEquals(newEntity.getCondiciones(), entity.getCondiciones());
        Assert.assertEquals(newEntity.getTipo(), entity.getTipo());

    }

    @Test(expected = BusinessLogicException.class)
    public void createSeguroContenidoNull() throws BusinessLogicException {
        SeguroEntity seguro = factory.manufacturePojo(SeguroEntity.class);
        seguro.setCaracteristicas(null);
        seguro.setCondiciones(null);
        seguro.setTipo(null);
        SeguroEntity result = seguroLogic.createSeguro(seguro);
    }

    /**
     * Prueba para consultar la lista de Seguros.
     */
    @Test
    public void getSegurosTest() {
        List<SeguroEntity> list = seguroLogic.getSeguros();
        Assert.assertEquals(data.size(), list.size());
        for (SeguroEntity entity : list) {
            boolean found = false;
            for (SeguroEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Seguro.
     */
    @Test
    public void getSeguroTest() {
        SeguroEntity entity = data.get(0);
        SeguroEntity resultEntity = seguroLogic.getSeguro(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(resultEntity.getCaracteristicas(), entity.getCaracteristicas());
        Assert.assertEquals(resultEntity.getCondiciones(), entity.getCondiciones());
        Assert.assertEquals(resultEntity.getTipo(), entity.getTipo());

    }

    /**
     * Prueba para actualizar un Seguro.
     */
    @Test
    public void updateSeguroTest() {
        SeguroEntity entity = data.get(0);
        SeguroEntity pojoEntity = factory.manufacturePojo(SeguroEntity.class);
        pojoEntity.setId(entity.getId());
        seguroLogic.updateSeguro(pojoEntity);
        SeguroEntity resp = em.find(SeguroEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCaracteristicas(), resp.getCaracteristicas());
        Assert.assertEquals(pojoEntity.getCondiciones(), resp.getCondiciones());
        Assert.assertEquals(pojoEntity.getTipo(), resp.getTipo());
    }

    /**
     * Prueba para eliminar un Seguro.
     *
     * @throws co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException
     */
    @Test
    public void deleteSeguroTest() throws BusinessLogicException {
        SeguroEntity entity = data.get(0);
        seguroLogic.deleteSeguro(entity.getId());
        SeguroEntity deleted = em.find(SeguroEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
