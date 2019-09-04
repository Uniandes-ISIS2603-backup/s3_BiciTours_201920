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
import javax.inject.Inject;
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
 * @author Juan Sebastián González Rojas
 */
@RunWith(Arquillian.class)
public class UsuarioLogicTest {
    
    private PodamFactory factory= new PodamFactoryImpl();
    @Inject
    private UsuarioLogic usuarioLogic;
    @Deployment
    public static JavaArchive createDeployment(){
      return ShrinkWrap.create(JavaArchive.class)
              .addPackage(UsuarioEntity.class.getPackage())
              .addPackage(UsuarioLogic.class.getPackage())
              .addPackage(UsuarioPersistence.class.getPackage())
              .addAsManifestResource("META-INF/persistence.xml","persistence.xml")
              .addAsManifestResource("META-INF/beans.xml","beans.xml");
    } 
    @Test
    public void createUsuario() throws BusinessLogicException{
        UsuarioEntity usuarioPrueba= factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity creacion=usuarioLogic.create(usuarioPrueba);
        Assert.assertNotNull(creacion);
    }
    //Prueba que la creación de un usuario con nombre nulo de como resultado una exception
    @Test (expected =BusinessLogicException.class)
    public void createUsuarioNombreNull() throws BusinessLogicException{
        UsuarioEntity usuarioNombreNull= factory.manufacturePojo(UsuarioEntity.class);
        usuarioNombreNull.setNombre(null);
        UsuarioEntity resultado= usuarioLogic.create(usuarioNombreNull);
    }
    //Prueba que la creación de un usuario con codigo nulo de como resultado una exception
    @Test (expected =BusinessLogicException.class)
    public void createUsuarioCodigoNull() throws BusinessLogicException{
        UsuarioEntity usuarioCodigoNull= factory.manufacturePojo(UsuarioEntity.class);
        usuarioCodigoNull.setCodigo(null);
        UsuarioEntity resultado= usuarioLogic.create(usuarioCodigoNull);
    }
}
