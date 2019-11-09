/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.RecomendacionLogic;
import co.edu.uniandes.csw.bicitours.ejb.RecomendacionLogic;
import co.edu.uniandes.csw.bicitours.entities.RecomendacionEntity;
import co.edu.uniandes.csw.bicitours.entities.RecomendacionEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.RecomendacionPersistence;
import co.edu.uniandes.csw.bicitours.persistence.RecomendacionPersistence;
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
public class RecomendacionLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RecomendacionLogic recomendacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RecomendacionEntity> data = new ArrayList<RecomendacionEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecomendacionEntity.class.getPackage())
                .addPackage(RecomendacionLogic.class.getPackage())
                .addPackage(RecomendacionPersistence.class.getPackage())
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
        em.createQuery("delete from RecomendacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RecomendacionEntity entity = factory.manufacturePojo(RecomendacionEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Recomendacion
     *
     * @throws co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException
     */
    @Test
    public void createRecomendacionTest() throws BusinessLogicException {
        RecomendacionEntity newEntity = factory.manufacturePojo(RecomendacionEntity.class);
        RecomendacionEntity result = recomendacionLogic.createRecomendacion(newEntity);
        Assert.assertNotNull(result);
        RecomendacionEntity entity = em.find(RecomendacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getIndumentaria(), entity.getIndumentaria());
        Assert.assertEquals(newEntity.getTipoBici(), entity.getTipoBici());

    }

    @Test(expected = BusinessLogicException.class)
    public void createRecomendacionContenidoNull() throws BusinessLogicException {
        RecomendacionEntity recomendacion = factory.manufacturePojo(RecomendacionEntity.class);
        recomendacion.setIndumentaria(null);
        recomendacion.setTipoBici(null);
        RecomendacionEntity result = recomendacionLogic.createRecomendacion(recomendacion);
    }

    /**
     * Prueba para consultar la lista de Recomendacions.
     */
    @Test
    public void getRecomendacionsTest() {
        List<RecomendacionEntity> list = recomendacionLogic.getRecomendacions();
        Assert.assertEquals(data.size(), list.size());
        for (RecomendacionEntity entity : list) {
            boolean found = false;
            for (RecomendacionEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Recomendacion.
     */
    @Test
    public void getRecomendacionTest() {
        RecomendacionEntity entity = data.get(0);
        RecomendacionEntity resultEntity = recomendacionLogic.getRecomendacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(resultEntity.getIndumentaria(), entity.getIndumentaria());
        Assert.assertEquals(resultEntity.getTipoBici(), entity.getTipoBici());

    }

    /**
     * Prueba para actualizar un Recomendacion.
     */
    @Test
    public void updateRecomendacionTest() {
        RecomendacionEntity entity = data.get(0);
        RecomendacionEntity pojoEntity = factory.manufacturePojo(RecomendacionEntity.class);
        pojoEntity.setId(entity.getId());
        recomendacionLogic.updateRecomendacion(pojoEntity);
        RecomendacionEntity resp = em.find(RecomendacionEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getIndumentaria(), resp.getIndumentaria());
        Assert.assertEquals(pojoEntity.getTipoBici(), resp.getTipoBici());
    }

    /**
     * Prueba para eliminar un Recomendacion.
     *
     * @throws co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException
     */
    @Test
    public void deleteRecomendacionTest() throws BusinessLogicException {
        RecomendacionEntity entity = data.get(0);
        recomendacionLogic.deleteRecomendacion(entity.getId());
        RecomendacionEntity deleted = em.find(RecomendacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
