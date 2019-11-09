/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.FotoLogic;
import co.edu.uniandes.csw.bicitours.entities.FotoEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
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
public class FotoLogicTest {
 private PodamFactory factory = new PodamFactoryImpl( );
    
    @Inject
    private FotoLogic fotoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<FotoEntity> data = new ArrayList();
    
    private List<TourEntity> dataTour = new ArrayList();
    
    @Deployment
    public static JavaArchive createDeployment( )
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(FotoEntity.class.getPackage( ))
                .addPackage(FotoLogic.class.getPackage( ))
                .addPackage(FotoPersistence.class.getPackage( ))
                .addAsManifestResource("Meta-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Configuración inicial de la prueba.
     */
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
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from FotoEntity").executeUpdate();
        em.createQuery("delete from TourEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++)
        {
            TourEntity tour = factory.manufacturePojo(TourEntity.class);
            em.persist(tour);
            dataTour.add(tour);
        }
        for (int i = 0; i < 3; i++) {
            
            FotoEntity foto = factory.manufacturePojo(FotoEntity.class);
            foto.setTour(dataTour.get(1));

            em.persist(foto);
            data.add(foto);
        }
    }
    
    /**
     * Test para comprobar que el foto es valido
     * @throws BusinessLogicException 
     */
    @Test
    public void createFotoTest( ) throws BusinessLogicException
    {
        FotoEntity newEntity = factory.manufacturePojo(FotoEntity.class);
        newEntity.setTour(dataTour.get(1));
        
        FotoEntity result = fotoLogic.createFoto(dataTour.get(1).getId(),newEntity);
        
        Assert.assertNotNull(result);
        FotoEntity entity = em.find(FotoEntity.class, result.getId());
        Assert.assertEquals(entity.getRuta(), result.getRuta());

    }
    
    
    /**
     * Prueba para consultar un Foto.
     */
    @Test
    public void getFotoTest() {
        FotoEntity entity = data.get(1);
        FotoEntity resultEntity = fotoLogic.getFoto(dataTour.get(1).getId(), entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        ////
    }
    
    
    
   
  
}
