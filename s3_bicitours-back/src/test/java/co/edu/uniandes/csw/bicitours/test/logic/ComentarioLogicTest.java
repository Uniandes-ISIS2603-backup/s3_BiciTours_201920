/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
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
public class ComentarioLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ComentarioLogic logica;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<ComentarioEntity> data = new ArrayList();

    private List<UsuarioEntity> usuarioData = new ArrayList();
    
    @Deployment
    public static JavaArchive createDeployment()
    {
         return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
            comentario.setCalificacion(2);
            comentario.setTexto("Hola");
            //comentario.setHead(null);
            //comentario.setRespuestas(null);
            comentario.setUsuario(usuarioData.get(0));

            em.persist(comentario);
            data.add(comentario);
        }
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        em.persist(usuario);
        usuario.getComentarios().add(data.get(1));
        data.get(1).setUsuario(usuario);
    }
    
    @Test
    public void createComentarioTest() throws BusinessLogicException
    {
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setCalificacion(3);
        comentario.setTexto("Hola");
        ComentarioEntity prueba = logica.createComentario(comentario);
        Assert.assertNotNull(prueba);
        prueba = em.find(ComentarioEntity.class, prueba.getId());
        Assert.assertEquals(comentario.getId(), prueba.getId());
        Assert.assertEquals(comentario.getCalificacion(), prueba.getCalificacion());
        Assert.assertEquals(comentario.getTexto(), prueba.getTexto());
    }
    
    @Test(expected = BusinessLogicException.class) 
    public void createComentarioNulo() throws BusinessLogicException
    {
        ComentarioEntity comentario = null;
        logica.createComentario(comentario);
    }
    
    @Test(expected = BusinessLogicException.class) 
    public void createComentarioConTextoNulo() throws BusinessLogicException
    {
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setTexto(null);
        comentario.setCalificacion(4);
        logica.createComentario(comentario);
    }
    
    @Test(expected = BusinessLogicException.class) 
    public void createComentarioConCalificacionNula() throws BusinessLogicException
    {
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setCalificacion(null);
        comentario.setTexto("hola");
        logica.createComentario(comentario);
    }
    
    @Test(expected = BusinessLogicException.class) 
    public void createComentarioSinTextoNiCalificacion() throws BusinessLogicException
    {
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setTexto("");
        comentario.setCalificacion(0);
        logica.createComentario(comentario);
    }
    
    @Test(expected = BusinessLogicException.class) 
    public void createComentarioConCalificacionFueraDeRango() throws BusinessLogicException
    {
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setTexto("");
        comentario.setCalificacion(6);
        logica.createComentario(comentario);
    }
    
    @Test(expected = BusinessLogicException.class) 
    public void createComentarioConTextoFueraDeLimite() throws BusinessLogicException
    {
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setTexto("Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia vo.");
        comentario.setCalificacion(0);
        logica.createComentario(comentario);
    }
    
    @Test
    public void getComentariosTest() {
        List<ComentarioEntity> list = logica.getComentarios();
        Assert.assertEquals(data.size(), list.size());
        for (ComentarioEntity comentario : list) {
            boolean found = false;
            for (ComentarioEntity prueba : data) {
                if (comentario.getId().equals(prueba.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

//    @Test
//    public void getComentarioTest() {
//        configTest();
//        ComentarioEntity comentario = data.get(0);
//        ComentarioEntity prueba = logica.getComentario(comentario.getId());
//        Assert.assertNotNull(prueba);
//        Assert.assertEquals(comentario.getId(), prueba.getId());
//        Assert.assertEquals(comentario.getCalificacion(), prueba.getCalificacion());
//        Assert.assertEquals(comentario.getTexto(), prueba.getTexto());
//    }
    
//    @Test
//    public void updateComentarioTest() throws BusinessLogicException {
//        configTest();
//        ComentarioEntity comentario = data.get(0);
//        ComentarioEntity prueba = factory.manufacturePojo(ComentarioEntity.class);
//        prueba.setId(comentario.getId());
//        prueba.setCalificacion(4);
//        prueba.setTexto("Chao");
//        logica.updateComentario(prueba.getId(), prueba);
//        ComentarioEntity comentarioA = em.find(ComentarioEntity.class, comentario.getId());
//        Assert.assertEquals(prueba.getId(), comentarioA.getId());
//        Assert.assertEquals(prueba.getCalificacion(), comentarioA.getCalificacion());
//        Assert.assertEquals(prueba.getTexto(), comentarioA.getTexto());
//    }
}
