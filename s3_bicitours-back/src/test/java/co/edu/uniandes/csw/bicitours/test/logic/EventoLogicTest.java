/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.EventoLogic;
import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.EventoPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
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
 * @author Michel Succar Medina
 */
@RunWith(Arquillian.class)
public class EventoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl( );
    
    @Inject
    private EventoLogic eventoLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<EventoEntity> data = new ArrayList();
    
    private List<TourEntity> dataTour = new ArrayList();
    
    @Deployment
    public static JavaArchive createDeployment( )
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EventoEntity.class.getPackage( ))
                .addPackage(EventoLogic.class.getPackage( ))
                .addPackage(EventoPersistence.class.getPackage( ))
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
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from TourEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++)
        {
            GregorianCalendar fti = new GregorianCalendar(2019, 9, 9, 9, 0);
            TourEntity tour = factory.manufacturePojo(TourEntity.class);
            tour.setFecha(fti.getTime());
            tour.setDuracion(60*24);
            em.persist(tour);
            dataTour.add(tour);
        }
        for (int i = 0; i < 3; i++) {
            
            Date fi;
            fi = new Date(2019,9,9,12+i,30);
            Date ff;
            ff = new Date(2019,9,9,13+i,0);
            
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            evento.setDescripcion("Sanduche de jamón y queso.");
            evento.setNombre("Almuerzo");
            evento.setHoraInicio(fi.getTime());
            evento.setHoraFin(ff.getTime());
            evento.setTour(dataTour.get(1));

            em.persist(evento);
            data.add(evento);
        }
    }
    
    /**
     * Test para comprobar que el evento es valido
     * @throws BusinessLogicException 
     */
    @Test
    public void createEventoTest( ) throws BusinessLogicException
    {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        Date fi = new Date(2020, 9, 9, 12, 30);
        Date ff = new Date(2020, 9, 9, 13, 0);
        newEntity.setHoraInicio(fi.getTime());
        newEntity.setHoraFin(ff.getTime());
        GregorianCalendar fti = new GregorianCalendar(2019,9,9,9,0);
        dataTour.get(1).setFecha(fti.getTime());
        dataTour.get(1).setDuracion(60*24);
        newEntity.setTour(dataTour.get(1));
        
        EventoEntity result = eventoLogic.createEventoEntity(newEntity, dataTour.get(1).getId());
        
        Assert.assertNotNull(result);
        System.out.println("Michel Succar" + result.getHoraFin());
        EventoEntity entity = em.find(EventoEntity.class, result.getId());
        System.out.println("Michel Succar" + entity.getHoraFin());
        Assert.assertEquals(entity.getNombre(), result.getNombre());
        Assert.assertEquals(entity.getDescripcion(), result.getDescripcion());
        Assert.assertEquals(entity.getHoraFin(), result.getHoraFin());
        Assert.assertEquals(entity.getHoraInicio(), result.getHoraInicio());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoNombreNull( ) throws BusinessLogicException
    {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setTour(dataTour.get(1));
        
        newEntity.setNombre(null);
        EventoEntity result = eventoLogic.createEventoEntity(newEntity, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoDescripcionNull( )throws BusinessLogicException
    {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setTour(dataTour.get(1));
        newEntity.setDescripcion(null);
        EventoEntity result = eventoLogic.createEventoEntity(newEntity, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoDescripcionFueraDeRango( )throws BusinessLogicException
    {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setTour(dataTour.get(1));
        newEntity.setDescripcion("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia vo.");
        EventoEntity result = eventoLogic.createEventoEntity(newEntity, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoHoraInicioNull( ) throws BusinessLogicException
    {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setTour(dataTour.get(1));
        newEntity.setHoraInicio(null);
        EventoEntity result = eventoLogic.createEventoEntity(newEntity, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoHoraFinNull( ) throws BusinessLogicException
    {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setTour(dataTour.get(1));
        newEntity.setHoraFin(null);
        EventoEntity result = eventoLogic.createEventoEntity(newEntity, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoHoraInicioMayorHoraFin( ) throws BusinessLogicException
    {
        Date fi;
        fi = new Date(2019,9,9,13,30);
        Date ff;
        ff = new Date(2019,9,9,13,0);
        
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setTour(dataTour.get(1));
        newEntity.setHoraInicio(fi.getTime());
        newEntity.setHoraFin(ff.getTime());
        EventoEntity result = eventoLogic.createEventoEntity(newEntity, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventosHoraInicioConflictiva( ) throws BusinessLogicException
    {
        Date fi1;
        fi1 = new Date(2019,9,9,12,30);
        Date ff1;
        ff1 = new Date(2019,9,9,13,0);
        
        EventoEntity newEntity1 = factory.manufacturePojo(EventoEntity.class);
        newEntity1.setTour(dataTour.get(1));
        newEntity1.setHoraInicio(fi1.getTime());
        newEntity1.setHoraFin(ff1.getTime());
        EventoEntity result = eventoLogic.createEventoEntity(newEntity1, dataTour.get(1).getId());
        
        Date fi2;
        fi2 = new Date(2019,9,9,12,45);
        Date ff2;
        ff2 = new Date(2019,9,9,13,15);
        
        EventoEntity newEntity2 = factory.manufacturePojo(EventoEntity.class);
        newEntity2.setTour(dataTour.get(1));
        newEntity2.setHoraInicio(fi2.getTime());
        newEntity2.setHoraFin(ff2.getTime());
        EventoEntity result2 = eventoLogic.createEventoEntity(newEntity2, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventosHoraFinConflictiva( ) throws BusinessLogicException
    {
        Date fi1;
        fi1 = new Date(2019,9,9,12,30);
        Date ff1;
        ff1 = new Date(2019,9,9,13,0);
        
        EventoEntity newEntity1 = factory.manufacturePojo(EventoEntity.class);
        newEntity1.setTour(dataTour.get(1));
        newEntity1.setHoraInicio(fi1.getTime());
        newEntity1.setHoraFin(ff1.getTime());
        EventoEntity result = eventoLogic.createEventoEntity(newEntity1, dataTour.get(1).getId());
        
        Date fi2;
        fi2 = new Date(2019,9,9,12,15);
        Date ff2;
        ff2 = new Date(2019,9,9,12,45);
        
        EventoEntity newEntity2 = factory.manufacturePojo(EventoEntity.class);
        newEntity2.setTour(dataTour.get(1));
        newEntity2.setHoraInicio(fi2.getTime());
        newEntity2.setHoraFin(ff2.getTime());
        EventoEntity result2 = eventoLogic.createEventoEntity(newEntity2, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventosHoraInicioIgual( ) throws BusinessLogicException
    {
        Date fi1;
        fi1 = new Date(2019,9,9,12,30);
        Date ff1;
        ff1 = new Date(2019,9,9,13,0);
        
        EventoEntity newEntity1 = factory.manufacturePojo(EventoEntity.class);
        newEntity1.setTour(dataTour.get(1));
        newEntity1.setHoraInicio(fi1.getTime());
        newEntity1.setHoraFin(ff1.getTime());
        EventoEntity result = eventoLogic.createEventoEntity(newEntity1, dataTour.get(1).getId());
        
        Date fi2;
        fi2 = new Date(2019,9,9,12,30);
        Date ff2;
        ff2 = new Date(2019,9,9,13,15);
        
        EventoEntity newEntity2 = factory.manufacturePojo(EventoEntity.class);
        newEntity2.setTour(dataTour.get(1));
        newEntity2.setHoraInicio(fi2.getTime());
        newEntity2.setHoraFin(ff2.getTime());
        EventoEntity result2 = eventoLogic.createEventoEntity(newEntity2, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventosHoraFinIgual( ) throws BusinessLogicException
    {
        Date fi1;
        fi1 = new Date(2019,9,9,12,30);
        Date ff1;
        ff1 = new Date(2019,9,9,13,0);
        
        EventoEntity newEntity1 = factory.manufacturePojo(EventoEntity.class);
        newEntity1.setTour(dataTour.get(1));
        newEntity1.setHoraInicio(fi1.getTime());
        newEntity1.setHoraFin(ff1.getTime());
        EventoEntity result = eventoLogic.createEventoEntity(newEntity1, dataTour.get(1).getId());
        
        Date fi2;
        fi2 = new Date(2019,9,9,12,15);
        Date ff2;
        ff2 = new Date(2019,9,9,13,0);
        
        EventoEntity newEntity2 = factory.manufacturePojo(EventoEntity.class);
        newEntity2.setTour(dataTour.get(1));
        newEntity2.setHoraInicio(fi2.getTime());
        newEntity2.setHoraFin(ff2.getTime());
        EventoEntity result2 = eventoLogic.createEventoEntity(newEntity2, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventosFueraTourInicio( ) throws BusinessLogicException
    {
        GregorianCalendar fti = new GregorianCalendar(2019, 9, 9,13,0);
        dataTour.get(1).setFecha(fti.getTime());
        dataTour.get(1).setDuracion(60);
        
        Date fi1;
        fi1 = new Date(2019,9,9,12,30);
        Date ff1;
        ff1 = new Date(2019,9,9,13,30);
        
        EventoEntity newEntity1 = factory.manufacturePojo(EventoEntity.class);
        newEntity1.setTour(dataTour.get(1));
        newEntity1.setHoraInicio(fi1.getTime());
        newEntity1.setHoraFin(ff1.getTime());
        EventoEntity result = eventoLogic.createEventoEntity(newEntity1, dataTour.get(1).getId());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventosFueraTourFinal( ) throws BusinessLogicException
    {
        GregorianCalendar fti = new GregorianCalendar(2019, 9, 9, 11,30);
        dataTour.get(1).setFecha(fti.getTime());
        dataTour.get(1).setDuracion(60);
        
        Date fi1;
        fi1 = new Date(2019,9,9,12,30);
        Date ff1;
        ff1 = new Date(2019,9,9,13,30);
        
        EventoEntity newEntity1 = factory.manufacturePojo(EventoEntity.class);
        newEntity1.setTour(dataTour.get(1));
        newEntity1.setHoraInicio(fi1.getTime());
        newEntity1.setHoraFin(ff1.getTime());
        EventoEntity result = eventoLogic.createEventoEntity(newEntity1, dataTour.get(1).getId());
    }
}
