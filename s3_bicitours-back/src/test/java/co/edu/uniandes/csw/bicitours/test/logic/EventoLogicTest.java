/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.EventoLogic;
import co.edu.uniandes.csw.bicitours.entities.EventoEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.EventoPersistence;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
 * @author Michel Succar Medina
 */
@RunWith(Arquillian.class)
public class EventoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl( );
    
    @Inject
    private EventoLogic eventoLogic;
    
    private EntityManager em;
    
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
    
    @Test
    public void createEvento( ) throws BusinessLogicException
    {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        EventoEntity result = eventoLogic.createEventoEntity(newEntity);
        System.out.println("Resultado..." + result.getId());
        System.out.println("Resultado..." + result.getNombre());
        Assert.assertNotNull(result);
        
        
        
        //EventoEntity entity = em.find(EventoEntity.class, result.getId());
        //Assert.assertEquals(entity.getNombre(), result.getNombre());
    }
    
    @Test (expected = BusinessLogicException.class)
    public void createEventoNombreNull( ) throws BusinessLogicException
    {
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setNombre(null);
        EventoEntity result = eventoLogic.createEventoEntity(newEntity);
    }
    
}
