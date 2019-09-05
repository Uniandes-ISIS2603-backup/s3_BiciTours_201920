/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.persistence;
import co.edu.uniandes.csw.bicitours.entities.SeguroEntity;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Maria Clara Noguera
 */
@RunWith(Arquillian.class)
public class SeguroPersistenceTest {

    @Inject
    private SeguroPersistence seguroPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<SeguroEntity> data = new ArrayList<SeguroEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SeguroEntity.class.getPackage())
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
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            SeguroEntity entity = factory.manufacturePojo(SeguroEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Seguro.
     */
    @Test
    public void createSeguroTest() {
        PodamFactory factory = new PodamFactoryImpl();
        SeguroEntity newEntity = factory.manufacturePojo(SeguroEntity.class);
        SeguroEntity result = seguroPersistence.create(newEntity);

        org.junit.Assert.assertNotNull(result);

        SeguroEntity entity = em.find(SeguroEntity.class, result.getId());

        org.junit.Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        org.junit.Assert.assertEquals(newEntity.getCaracteristicas(), entity.getCaracteristicas());
        org.junit.Assert.assertEquals(newEntity.getCondiciones(), entity.getCondiciones());
    }

    /**
     * Prueba para consultar la lista de Seguros.
     */
    @Test
    public void getSegurosTest() {
        List<SeguroEntity> list = seguroPersistence.findAll();
        org.junit.Assert.assertEquals(data.size(), list.size());
        for (SeguroEntity ent : list) {
            boolean found = false;
            for (SeguroEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Seguro.
     */
    @Test
    public void getSeguroTest() {
        SeguroEntity entity = data.get(0);
        SeguroEntity newEntity = seguroPersistence.find(entity.getId());
        org.junit.Assert.assertNotNull(newEntity);
        org.junit.Assert.assertEquals(newEntity.getTipo(), entity.getTipo());
        org.junit.Assert.assertEquals(newEntity.getCaracteristicas(), entity.getCaracteristicas());
        org.junit.Assert.assertEquals(newEntity.getCondiciones(), entity.getCondiciones());
    }

    /**
     * Prueba para eliminar un Seguro.
     */
    @Test
    public void deleteSeguroTest() {
        SeguroEntity entity = data.get(0);
        seguroPersistence.delete(entity.getId());
        SeguroEntity deleted = em.find(SeguroEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Seguro.
     */
    @Test
    public void updateSeguroTest() {
        SeguroEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        SeguroEntity newEntity = factory.manufacturePojo(SeguroEntity.class);

        newEntity.setId(entity.getId());

        seguroPersistence.update(newEntity);

        SeguroEntity resp = em.find(SeguroEntity.class, entity.getId());

           org.junit.Assert.assertEquals(newEntity.getTipo(), resp.getTipo());
        org.junit.Assert.assertEquals(newEntity.getCaracteristicas(), resp.getCaracteristicas());
        org.junit.Assert.assertEquals(newEntity.getCondiciones(), resp.getCondiciones());
    }
}
