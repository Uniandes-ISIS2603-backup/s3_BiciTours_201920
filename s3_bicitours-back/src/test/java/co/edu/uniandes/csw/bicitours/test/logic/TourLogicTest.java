/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.TourLogic;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Jhuliana Barrios
 */
@RunWith(Arquillian.class)
public class TourLogicTest{
        
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private TourLogic tourLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    private TourEntity tourPrueba;
    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TourEntity.class.getPackage())
                .addPackage(TourLogic.class.getPackage())
                .addPackage(TourPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * El escenario crea un tour que cumple todas las reglas de negocio
     */
    public TourEntity setupEscenario()throws BusinessLogicException
    {
        tourPrueba= factory.manufacturePojo(TourEntity.class);
        Date fechaProv = new Date();
        fechaProv.setYear(fechaProv.getYear()+5);
        tourPrueba.setFecha(fechaProv);
        int random = (int)(Math.random() * Integer.MAX_VALUE + 1);
        tourPrueba.setDuracion(random);
        tourPrueba.setCosto(random);
        TourEntity r=tourLogic.createTour(tourPrueba);
        return r;
    }
    
    /**
     * Prueba que un tour que cumpla con las reglas de negocio sea creado
     * @throws BusinessLogicException
     */
    @Test
    public void crearTour()throws BusinessLogicException{
        TourEntity r=setupEscenario();
        Assert.assertNotNull(r);
    }
    
    /**
     * Prueba que no se cree un tour con un nombre no existene
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearNombreNulo()throws BusinessLogicException{
        setupEscenario();
        tourPrueba.setNombre(null);
        tourLogic.createTour(tourPrueba);
    }
    
    /**
     * Prueba que no se cree un tour con un nombre vacio
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearNombreVacio()throws BusinessLogicException{
        setupEscenario();
        tourPrueba.setNombre("   ");
        tourLogic.createTour(tourPrueba);
    }
    
    /**
     * Prueba que no se cree un tour con una duración inválida, la duración solo es inválida si es negativa
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearConDuracionInvalida() throws BusinessLogicException{
        setupEscenario();
        int random = (int)(Math.random() * Integer.MIN_VALUE + -1);
        tourPrueba.setDuracion(random);
        tourLogic.createTour(tourPrueba);        
    }
    
    /**
     * Prueba que no se cree un tour con una fecha inválida, una fecha es inválida si es antes de la fecha actual o al mismo instante en que se quiere crear el tour
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearFechaInvalida() throws BusinessLogicException{
        setupEscenario();
        Date fechaProv = new Date();
        fechaProv.setYear(fechaProv.getYear()-5);
        tourPrueba.setFecha(fechaProv);
        tourLogic.createTour(tourPrueba);        
    }
    
    /**
     * Prueba que no se cree un tour con una dificultad inválida, una dificultad es inválida si es nula,
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearDificultadInvalida() throws BusinessLogicException{
        setupEscenario();
        tourPrueba.setDificultad(null);
        tourLogic.createTour(tourPrueba);        
    }
    
    /**
     * Prueba que no se cree un tour con una descripción nula
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearDescripcionInvalida() throws BusinessLogicException{
        setupEscenario();
        tourPrueba.setDescripcion(null);
        tourLogic.createTour(tourPrueba);        
    }
    
    /**
     * Prueba que no se cree un tour con una descripción vacia
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearDescripcionVacia()throws BusinessLogicException{
        setupEscenario();
        tourPrueba.setDescripcion("   ");
        tourLogic.createTour(tourPrueba);
    }
    
    /**
     * Prueba que no se cree un tour con un lugar vacio
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearLugarVacio()throws BusinessLogicException{
        setupEscenario();
        tourPrueba.setLugar("   ");
        tourLogic.createTour(tourPrueba);
    }
    
    /**
     * Prueba que no se cree un tour con un lugar nulo
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearLugarInvalido() throws BusinessLogicException{
        setupEscenario();
        tourPrueba.setLugar(null);
        tourLogic.createTour(tourPrueba);        
    }
    
        /**
     * Prueba que no se cree un tour con un costo inválido
     * @throws BusinessLogicException 
     */
    @Test (expected = BusinessLogicException.class)
    public void crearCostoInvalido() throws BusinessLogicException{
        setupEscenario();
        int random = (int)(Math.random() * Integer.MIN_VALUE + -1);
        tourPrueba.setCosto(random);
        tourLogic.createTour(tourPrueba);        
    }
  
}
