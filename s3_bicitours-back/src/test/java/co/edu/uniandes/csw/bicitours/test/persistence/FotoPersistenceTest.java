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
    private FotoPersistence ep;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<FotoEntity> data = new ArrayList<FotoEntity>();
    
    private List<TourEntity> dataTour = new ArrayList<TourEntity>();
    
    @Deployment
    public static JavaArchive createDeployment( )
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FotoEntity.class.getPackage())
                .addPackage(FotoPersistence.class.getPackage())
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
        em.createQuery("delete from FotoEntity").executeUpdate();
        em.createQuery("delete from TourEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para las pruebas
     */
    private void insertData() 
    {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) 
        {
            TourEntity entity = factory.manufacturePojo(TourEntity.class);
            em.persist(entity);
            dataTour.add(entity);
        }
        for (int i = 0; i < 3; i++) 
        {
            FotoEntity entity = factory.manufacturePojo(FotoEntity.class);
            if (i == 0) {
                entity.setTour(dataTour.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createFotoTest( )
    {
        PodamFactory factory = new PodamFactoryImpl( );
        FotoEntity foto = factory.manufacturePojo(FotoEntity.class);
        FotoEntity result = ep.create(foto);
        Assert.assertNotNull(result);
        
        FotoEntity entity = em.find(FotoEntity.class, result.getId( ));
        Assert.assertEquals(foto.getRuta( ), entity.getRuta( ));

    }
    
    /**
     * Prueba para encontrar un Foto.
     */
    @Test
    public void findFotoTest()
    {
        FotoEntity foto = data.get(0);
        FotoEntity newEntity = ep.find(dataTour.get(0).getId(), foto.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(foto.getRuta( ), newEntity.getRuta( ));
    }
    
    /**
     * Prueba para consultar la lista de Fotos.
     */
    @Test
    public void findAllFotosTest() 
    {
 /**       List<FotoEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (FotoEntity foto : list) 
        {
            boolean found = false;
            for (FotoEntity entity : data) 
            {
                if (foto.getId().equals(entity.getId())) 
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
        * */
    }
    
    /**
     * Prueba para actualizar un Foto.
     */
    @Test
    public void updateFotoTest() {
        FotoEntity foto = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);

        newEntity.setId(foto.getId());

        ep.update(newEntity);

        FotoEntity resp = em.find(FotoEntity.class, foto.getId());

        Assert.assertEquals(resp.getRuta( ), newEntity.getRuta( ));
    }
    
    /**
     * Prueba para eliminar un Foto.
     */
    @Test
    public void deleteFotoTest() {
        FotoEntity entity = data.get(0);
        ep.delete(data.get(0).getTour().getId(),entity.getId());
        FotoEntity deleted = em.find(FotoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}