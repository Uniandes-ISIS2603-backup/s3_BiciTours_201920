/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.persistence;

import co.edu.uniandes.csw.bicitours.entities.RecomendacionEntity;
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
public class RecomendacionPersistenceTest {
    @Inject
    private RecomendacionPersistence ep;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<RecomendacionEntity> data = new ArrayList<RecomendacionEntity>();
    
    @Deployment
    public static JavaArchive createDeployment( )
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecomendacionEntity.class.getPackage())
                .addPackage(RecomendacionPersistence.class.getPackage())
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
     * Limpia las tablas implicadas en las pruebas
     */
    private void clearData() 
    {
        em.createQuery("delete from RecomendacionEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para las pruebas
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            RecomendacionEntity entity = factory.manufacturePojo(RecomendacionEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createRecomendacionTest( )
    {
        PodamFactory factory = new PodamFactoryImpl( );
        RecomendacionEntity recomendacion = factory.manufacturePojo(RecomendacionEntity.class);
        RecomendacionEntity result = ep.create(recomendacion);
        Assert.assertNotNull(result);
        
        RecomendacionEntity entity = em.find(RecomendacionEntity.class, result.getId( ));
        Assert.assertEquals(recomendacion.getIndumentaria(), entity.getIndumentaria());
        Assert.assertEquals(recomendacion.getTipoBici(), entity.getTipoBici());
    }
    
    /**
     * Prueba para encontrar un Recomendacion.
     */
    @Test
    public void findRecomendacionTest()
    {
        RecomendacionEntity recomendacion = data.get(0);
        RecomendacionEntity newEntity = ep.find(recomendacion.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(recomendacion.getIndumentaria(), newEntity.getIndumentaria());
        Assert.assertEquals(recomendacion.getTipoBici(), newEntity.getTipoBici());
    }
    
    /**
     * Prueba para consultar la lista de Recomendacions.
     */
    @Test
    public void findAllRecomendacionsTest() 
    {
        List<RecomendacionEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (RecomendacionEntity recomendacion : list) 
        {
            boolean found = false;
            for (RecomendacionEntity entity : data) 
            {
                if (recomendacion.getId().equals(entity.getId())) 
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para actualizar un Recomendacion.
     */
    @Test
    public void updateRecomendacionTest() {
        RecomendacionEntity recomendacion = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        RecomendacionEntity newEntity = factory.manufacturePojo(RecomendacionEntity.class);

        newEntity.setId(recomendacion.getId());

        ep.update(newEntity);

        RecomendacionEntity resp = em.find(RecomendacionEntity.class, recomendacion.getId());

        Assert.assertEquals(newEntity.getIndumentaria(), resp.getIndumentaria());
        Assert.assertEquals(newEntity.getTipoBici(), resp.getTipoBici());
    }
    
    /**
     * Prueba para eliminar un Recomendacion.
     */
    @Test
    public void deleteRecomendacionTest() {
        RecomendacionEntity entity = data.get(0);
        ep.delete(entity.getId());
        RecomendacionEntity deleted = em.find(RecomendacionEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
