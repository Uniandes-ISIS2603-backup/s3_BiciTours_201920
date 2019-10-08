/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.ComentarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioComentarioLogic;
import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.entities.ComentarioEntity;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
/**
 * Pruebas de logica de la relacion Usuario - Comentario
 * @author Juan Sebastián González Rojas
 */
@RunWith(Arquillian.class)
public class UsuarioComentarioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    //Clases que serán utilizadas en pruebas
    @Inject
    private UsuarioLogic usuarioLogic;
    @Inject
    private UsuarioComentarioLogic relacionLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    //Listas para almacenar información aleatoria
    private List<UsuarioEntity> usuariosData = new ArrayList<UsuarioEntity>();

    private List<ComentarioEntity> comentariosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(UsuarioEntity.class.getPackage())
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(UsuarioComentarioLogic.class.getPackage())
                .addPackage(UsuarioPersistence.class.getPackage())
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
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ComentarioEntity entity = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(entity);
            comentariosData.add(entity);
        }
        for (int i = 0; i < 3; i++) {
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(usuario);
            usuariosData.add(usuario);
            if (i == 0) {
                //comentariosData.get(i).setUsuario(usuario);
            }
        }
    }

    /**
     * Prueba para asociar un Comentario existente a un Usuario.
     */
//    @Test
//    public void addComentarioTest() {
//        UsuarioEntity usuario = usuariosData.get(0);
//        ComentarioEntity comentario = comentariosData.get(1); //Comentario aleatorio obtenido de persistencia
//        ComentarioEntity comentarioMetodo = relacionLogic.addComentario(usuario.getId(), comentario.getId()); //comentario obtenido por método
//
//        Assert.assertNotNull(comentarioMetodo); //se asegura que la respuesta del método no sea nula
//        //Se verifica que ambos comentarios obtenidos sean iguales
//        Assert.assertEquals(comentario.getId(), comentarioMetodo.getId());
//        //Assert.assertEquals(comentario.getUsuario(), comentarioMetodo.getUsuario());
//        Assert.assertEquals(comentario.getCalificacion(), comentarioMetodo.getCalificacion());
//        Assert.assertEquals(comentario.getTexto(), comentarioMetodo.getTexto());
//    }

    /**
     * Prueba para obtener una colección de instancias de Comentario asociadas a una
     * instancia Usuario.
     */
//    @Test
//    public void getComentariosTest() {
//        List<ComentarioEntity> list = relacionLogic.getComentarios(usuariosData.get(0).getId());//se obtiene la lista de comentarios por el método
//        Assert.assertEquals(1, list.size()); //se verifica el tamaño de la lista
//    }

    /**
     * Prueba para obtener una instancia de Comentario asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
//    @Test
//    public void getComentarioTest() throws BusinessLogicException {
//        UsuarioEntity usuarioTest = usuariosData.get(0); //se obtiene el primer usuario aleatorio
//        ComentarioEntity comentarioEntity = comentariosData.get(0); //se obtiene el primer comentario asociado a este desde la persistencia
//        ComentarioEntity comentarioMetodo = relacionLogic.getComentario(usuarioTest.getId(), comentarioEntity.getId()); //se obtiene este mismo comentario desde logic
//        //Se verifica que ambos comentarios obtenidos sean iguales
//        Assert.assertEquals(comentarioEntity.getId(), comentarioMetodo.getId());
//        //Assert.assertEquals(comentarioEntity.getUsuario(), comentarioMetodo.getUsuario());
//        Assert.assertEquals(comentarioEntity.getCalificacion(), comentarioMetodo.getCalificacion());
//        Assert.assertEquals(comentarioEntity.getTexto(), comentarioMetodo.getTexto());
//    }
    
    /**
     * Prueba para obtener una instancia de Comentario asociada a una instancia
     * Usuario.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void setComentariosTest() throws BusinessLogicException {
        UsuarioEntity usuarioTest = usuariosData.get(0);
        //se establece como comentarios del usuario todos los comentarios aleatorios de la base de datos
        List<ComentarioEntity> comentariosMetodo = relacionLogic.setComentarios(usuarioTest.getId(), comentariosData); 
        Assert.assertEquals(comentariosData.size(), comentariosMetodo.size()); //se verifica el tamaño de la lista       
    }

    /**
     * Prueba para obtener una instancia de Comentario asociada a una instancia
     * Usuario que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getComentario2Test() throws BusinessLogicException {
        UsuarioEntity usuarioTest = usuariosData.get(0);
        ComentarioEntity comentario = comentariosData.get(1);
        relacionLogic.getComentario(usuarioTest.getId(), comentario.getId());
    }
}
