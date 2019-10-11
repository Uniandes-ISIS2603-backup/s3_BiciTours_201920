/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.persistence;

import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
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
 * @author Michel Succar
 * 
 */
@RunWith(Arquillian.class)
public class EventoPersistanceTest {
    
    @Inject
    private EventoPersistence ep;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<EventoEntity> data = new ArrayList<EventoEntity>();
    
    private List<TourEntity> dataTour = new ArrayList<TourEntity>();
    
    @Deployment
    public static JavaArchive createDeployment( )
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
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
        em.createQuery("delete from EventoEntity").executeUpdate();
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
            EventoEntity entity = factory.manufacturePojo(EventoEntity.class);
            if (i == 0) {
                entity.setTour(dataTour.get(0));
            }
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createEventoTest( )
    {
        PodamFactory factory = new PodamFactoryImpl( );
        EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = ep.create(evento);
        Assert.assertNotNull(result);
        
        EventoEntity entity = em.find(EventoEntity.class, result.getId( ));
        Assert.assertEquals(evento.getNombre( ), entity.getNombre( ));
        Assert.assertEquals(evento.getDescripcion(), entity.getDescripcion());
        Assert.assertEquals(evento.getHoraFin(), entity.getHoraFin());
        Assert.assertEquals(evento.getHoraInicio(), entity.getHoraInicio());
    }
    
    /**
     * Prueba para encontrar un Evento.
     */
    @Test
    public void findEventoTest()
    {
        EventoEntity evento = data.get(0);
        EventoEntity newEntity = ep.find(dataTour.get(0).getId(), evento.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(evento.getNombre( ), newEntity.getNombre( ));
        Assert.assertEquals(evento.getDescripcion(), newEntity.getDescripcion());
        Assert.assertEquals(evento.getHoraFin(), newEntity.getHoraFin());
        Assert.assertEquals(evento.getHoraInicio(), newEntity.getHoraInicio());
    }
    
    /**
     * Prueba para consultar la lista de Eventos.
     */
    @Test
    public void findAllEventosTest() 
    {
        List<EventoEntity> list = ep.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (EventoEntity evento : list) 
        {
            boolean found = false;
            for (EventoEntity entity : data) 
            {
                if (evento.getId().equals(entity.getId())) 
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para actualizar un Evento.
     */
    @Test
    public void updateEventoTest() {
        EventoEntity evento = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);

        newEntity.setId(evento.getId());

        ep.update(newEntity);

        EventoEntity resp = em.find(EventoEntity.class, evento.getId());

        Assert.assertEquals(newEntity.getNombre( ), resp.getNombre( ));
        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getHoraFin(), resp.getHoraFin());
        Assert.assertEquals(newEntity.getHoraInicio(), resp.getHoraInicio());
    }
    
    /**
     * Prueba para eliminar un Evento.
     */
    @Test
    public void deleteEventoTest() {
        EventoEntity entity = data.get(0);
        ep.delete(entity.getId());
        EventoEntity deleted = em.find(EventoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}