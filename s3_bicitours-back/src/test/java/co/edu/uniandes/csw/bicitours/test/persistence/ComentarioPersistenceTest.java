/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.persistence;

import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.persistence.ComentarioPersistence;
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
 * @author JuanRueda
 */
@RunWith(Arquillian.class)
public class ComentarioPersistenceTest {
    
    @Inject
    private ComentarioPersistence cp;
    
    @PersistenceContext
    private EntityManager em;
     
    @Inject
    UserTransaction utx;
    
    private List<ComentarioEntity> data = new ArrayList<ComentarioEntity>();
    
     /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() 
    {
        try 
        {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            try 
            {
                utx.rollback();
            } 
            catch (Exception e1) 
            {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ComentarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Deployment
    public static JavaArchive createDeployment()
    {
         return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Test
    public void createComentarioTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newComentarioEntity = factory.manufacturePojo(ComentarioEntity.class);
        ComentarioEntity comentarioEntity = cp.create(newComentarioEntity);
        Assert.assertNotNull(comentarioEntity);
        ComentarioEntity entity = em.find(ComentarioEntity.class, comentarioEntity.getId());
        Assert.assertEquals(newComentarioEntity.getTexto(), entity.getTexto());
        Assert.assertEquals(newComentarioEntity.getCalificacion(), entity.getCalificacion());
    }
    
    /**
     * Prueba para consultar un Comentario.
     */
    @Test
    public void findComentarioTest() {
        ComentarioEntity entity = data.get(0);
        ComentarioEntity newEntity = cp.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getTexto(), newEntity.getTexto());
        Assert.assertEquals(entity.getCalificacion(), newEntity.getCalificacion());
    }
    
    /**
     * Prueba para consultar la lista de Comentarios.
     */
    @Test
    public void findAllComentariosTest() {
        List<ComentarioEntity> list = cp.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ComentarioEntity ent : list) 
        {
            boolean found = false;
            for (ComentarioEntity entity : data) 
            {
                if (ent.getId().equals(entity.getId())) 
                {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para actualizar un Comentario.
     */
    @Test
    public void updateComentarioTest() {
        ComentarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        newEntity.setId(entity.getId());
        cp.update(newEntity);
        ComentarioEntity resp = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertEquals(newEntity.getTexto(), resp.getTexto());
        Assert.assertEquals(newEntity.getCalificacion(), resp.getCalificacion());
    }
    
    /**
     * Prueba para eliminar un Comentario.
     */
    @Test
    public void deleteComentarioTest() {
        ComentarioEntity entity = data.get(0);
        cp.delete(entity.getId());
        ComentarioEntity deleted = em.find(ComentarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
