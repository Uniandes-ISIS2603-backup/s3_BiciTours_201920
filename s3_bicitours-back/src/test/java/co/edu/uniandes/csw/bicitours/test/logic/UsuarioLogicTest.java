/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.logic;

import co.edu.uniandes.csw.bicitours.ejb.UsuarioLogic;
import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
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
 * @author Juan Sebastián González Rojas
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {
    
    private PodamFactory factory= new PodamFactoryImpl();
    @Inject
    private UsuarioLogic usuarioLogic;
    @PersistenceContext
    private EntityManager em; //entityManager para no utilizar nuestros métodos y validar desde la persistencia
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();//Lista para almacenamiento de usuarios aleatorios
    @Inject
    UserTransaction utx;
    
    @Deployment
    public static JavaArchive createDeployment(){
      return ShrinkWrap.create(JavaArchive.class)
              .addPackage(UsuarioEntity.class.getPackage())
              .addPackage(UsuarioLogic.class.getPackage())
              .addPackage(UsuarioPersistence.class.getPackage())
              .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
              .addAsManifestResource("META-INF/beans.xml","beans.xml");
    } 
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
    @Test
    public void createUsuario() throws BusinessLogicException{ 
        //Esta prueba se implementa desde el caso existente hasta el caso no existente
        UsuarioEntity usuarioPrueba= factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity creacion=usuarioLogic.create(usuarioPrueba);
        Assert.assertNotNull(creacion);
        //Mejora de prueba mediante implementación de un Entity Manager
        UsuarioEntity usuarioBuscado= em.find(UsuarioEntity.class, creacion.getId());
        Assert.assertEquals(usuarioBuscado.getNombre(), creacion.getNombre());
    }
    /**
    Prueba que la creación de un usuario con nombre nulo de como resultado una exception
    */
    @Test (expected =BusinessLogicException.class)
    public void createUsuarioNombreNull() throws BusinessLogicException{
        UsuarioEntity usuarioNombreNull= factory.manufacturePojo(UsuarioEntity.class);
        usuarioNombreNull.setNombre(null);
        UsuarioEntity resultado= usuarioLogic.create(usuarioNombreNull);//se espera un error
    }
    /**
    Prueba que la creación de un usuario con codigo nulo de como resultado una exception
    */
    @Test (expected =BusinessLogicException.class)
    public void createUsuarioCodigoNull() throws BusinessLogicException{
        UsuarioEntity usuarioCodigoNull= factory.manufacturePojo(UsuarioEntity.class);
        usuarioCodigoNull.setCodigo(null);
        UsuarioEntity resultado= usuarioLogic.create(usuarioCodigoNull);//se espera un error
    }
    /**
    Prueba que la creación de un usuario con correo nulo de como resultado una exception
    */
    @Test (expected =BusinessLogicException.class)
    public void createUsuarioCorreoNull() throws BusinessLogicException{
        UsuarioEntity usuarioCorreoNull= factory.manufacturePojo(UsuarioEntity.class);
        usuarioCorreoNull.setCorreo(null);
        UsuarioEntity resultado= usuarioLogic.create(usuarioCorreoNull);//se espera un error
    }
    /**
    Prueba que la creación de un usuario con nombre repetido de como resultado una exception
    */
    @Test (expected =BusinessLogicException.class)
    public void createUsuarioNombreRepetido() throws BusinessLogicException{
        UsuarioEntity usuarioNombreRepetido= factory.manufacturePojo(UsuarioEntity.class);
        usuarioNombreRepetido.setNombre(data.get(0).getNombre());
        UsuarioEntity resultado= usuarioLogic.create(usuarioNombreRepetido);//se espera un error
    }
    /**
    Prueba que la creación de un usuario con nombre repetido de como resultado una exception
    */
    @Test (expected =BusinessLogicException.class)
    public void createUsuarioCorreoRepetido() throws BusinessLogicException{
        UsuarioEntity usuarioCorreoRepetido= factory.manufacturePojo(UsuarioEntity.class);
        usuarioCorreoRepetido.setCorreo(data.get(0).getCorreo());
        UsuarioEntity resultado= usuarioLogic.create(usuarioCorreoRepetido);//se espera un error
    }
    
    //Métodos adicionales
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from UsuarioEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para diferentes usuarios, de tal manera que puedan ser testeados todos los métodos
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) { //se crean tres usuarios para el análisis
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
}
