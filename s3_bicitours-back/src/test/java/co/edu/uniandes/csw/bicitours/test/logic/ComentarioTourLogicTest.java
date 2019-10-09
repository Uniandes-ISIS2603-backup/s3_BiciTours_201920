/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.ComentarioTourLogic;
import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.TourEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.TourPersistence;
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
 * @author JuanRueda
 */
@RunWith(Arquillian.class)
public class ComentarioTourLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ComentarioLogic comentarioLogic;
    @Inject
    private ComentarioTourLogic comentarioTourLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<TourEntity> data = new ArrayList<TourEntity>();

    private List<ComentarioEntity> comentariosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(TourEntity.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
                .addPackage(TourPersistence.class.getPackage())
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
        em.createQuery("delete from ComentarioEntity").executeUpdate();
        em.createQuery("delete from TourEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentarios = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(comentarios);
            comentariosData.add(comentarios);
        }
        for (int i = 0; i < 3; i++) {
            TourEntity entity = factory.manufacturePojo(TourEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                comentariosData.get(i).setTour(entity);
            }
        }
    }

    /**
     * Prueba para remplazar las instancias de Comentarios asociadas a una instancia
     * de Tour.
     */
    @Test
    public void replaceTourTest() {
        ComentarioEntity entity = comentariosData.get(0);
        comentarioTourLogic.replaceTour(entity.getId(), data.get(1).getId());
        entity = comentarioLogic.getComentario(entity.getId());
        Assert.assertEquals(entity.getTour(), data.get(1));
    }

    /**
     * Prueba para desasociar un Comentario existente de un Tour existente
     *
     */
    @Test
    public void removeComentariosTest() {
        comentarioTourLogic.removeTour(comentariosData.get(0).getId());
        ComentarioEntity response = comentarioLogic.getComentario(comentariosData.get(0).getId());
        Assert.assertNull(response.getTour());
    }
}