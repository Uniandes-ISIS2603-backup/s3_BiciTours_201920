/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.bicitours.test.persistence;

import co.edu.uniandes.csw.bicitours.entities.UsuarioEntity;
import co.edu.uniandes.csw.bicitours.persistence.UsuarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
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
 * @author Juan Sebastián González Rojas
 */
@RunWith(Arquillian.class)
public class UsuarioPersistenceTest {
    @Inject
    private UsuarioPersistence up; //instancia de persistencia que crea el contenedor
    @PersistenceContext
    private EntityManager em; //entityManager para no utilizar nuestros métodos y validar desde la persistencia
    private List<UsuarioEntity> data = new ArrayList<UsuarioEntity>();//Lista para almacenamiento de usuarios aleatorios
    @Inject 
    UserTransaction utx;
    
    @Deployment
    public static JavaArchive createDeployment(){
     return ShrinkWrap.create(JavaArchive.class).addClass(UsuarioEntity.class).addClass(UsuarioPersistence.class).addAsManifestResource("META-INF/persistence.xml","persistence.xml").addAsManifestResource("META-INF/beans.xml","beans.xml");   
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
     * Prueba para crear un Usuario.
     */
    @Test
    public void createTest(){
        
        PodamFactory factory= new PodamFactoryImpl();
        UsuarioEntity usuario=factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity result=up.create(usuario);
        //Verificación del método por su resultado
        Assert.assertNotNull(result); //este no debe ser nulo
        
        UsuarioEntity entity=em.find(UsuarioEntity.class,result.getId());//búsqueda en persistencia
        Assert.assertEquals(usuario.getNombre(), entity.getNombre()); //el nombre de la entidad aleatoria debe ser igual al nombre del objeto encontrado
        Assert.assertEquals(usuario.getCodigo(), entity.getCodigo()); //el código de la entidad aleatoria debe ser igual al código del objeto encontrado
        Assert.assertEquals(usuario.getDeuda(), entity.getDeuda()); //el deuda de la entidad aleatoria debe ser igual al deuda del objeto encontrado
        Assert.assertEquals(usuario.isEsAdmin(), entity.isEsAdmin()); //la entidad aleatoria debe ser del mismo tipo de usuario que el objeto encontrado
        Assert.assertEquals(usuario.isPago(), entity.isPago()); //el pago entidad aleatoria debe estar en el mismo estado que el del objeto encontrado
    }
    /**
     * Prueba para consultar un Usuario específico.
     */
    @Test
    public void getUsuarioTest(){
        UsuarioEntity usuarioBuscado = data.get(1); //se utiliza el segundo usuario definido aleatoriamente
        UsuarioEntity usuarioMetodo= up.find(usuarioBuscado.getId()); //se busca en la base de datos con el método definido en UsuarioPersistence
        
        Assert.assertEquals(usuarioMetodo.getNombre(), usuarioBuscado.getNombre()); //el nombre de la entidad en data debe ser igual al nombre del obtenido por find
        Assert.assertEquals(usuarioMetodo.getCodigo(), usuarioBuscado.getCodigo()); //el código de la entidad en data debe ser igual al código del obtenido por find
        Assert.assertEquals(usuarioMetodo.getDeuda(), usuarioBuscado.getDeuda()); //el deuda de la entidad aleatoria debe ser igual al deuda del obtenido por find
        Assert.assertEquals(usuarioMetodo.isEsAdmin(), usuarioBuscado.isEsAdmin()); //la entidad en data debe ser del mismo tipo de usuario que el obtenido por find
        Assert.assertEquals(usuarioMetodo.isPago(), usuarioBuscado.isPago()); //el pago entidad en data debe estar en el mismo estado que el del obtenido por find
    }
    /**
     * Prueba para consultar la lista de Usuarios.
     */
     @Test
    public void getUsuariosTest() {
        List<UsuarioEntity> list = up.findAll(); //implementación en persistencia a ser evaluada
        Assert.assertEquals(data.size(), list.size()); //verifica el tamaño de ambas listas
        boolean found = false;
        for (UsuarioEntity ent : list) { //se define ent como todo elemento usuario en la lista list
            found = false;
            for (UsuarioEntity entity : data) { //se verifica para toda entidad en el data guardado que exista una entidad igual en la lista
                if (ent.getId().equals(entity.getId())) { //la igualdad se implementa con Id como criterio
                    found = true;
                }
            }
            Assert.assertTrue(found); //en cada iteración se verifica que el par exista 
       }
   }

    /**
     * Prueba para la actualización de la información de un usuario.
     */
    @Test
    public void updateUsuarioTest(){
        UsuarioEntity usuarioAntiguo= data.get(0); //se actualizará el primer usuario creado aleatoriamente
        PodamFactory factory= new PodamFactoryImpl(); //factory para producir la actualización de manera aleatoria
        UsuarioEntity usuarioActualizado=factory.manufacturePojo(UsuarioEntity.class); //usuario con nuevos parámetros aleatorios
        
        //Se asocia el usuarioActualizado asignándole la misma id del usuarioAntiguo
        usuarioActualizado.setId(usuarioAntiguo.getId());
        
        up.update(usuarioActualizado);//se ejecuta el método definido en persistencia
        UsuarioEntity usuarioModificado= up.find(usuarioActualizado.getId()); // se obtiene el usuario de la base de datos
        
        //Verificación de parámetros
        Assert.assertEquals(usuarioModificado.getNombre(), usuarioActualizado.getNombre()); //el nombre de la entidad en la base de datos debe ser igual al nombre del utilizado en el método
        Assert.assertEquals(usuarioModificado.getCodigo(), usuarioActualizado.getCodigo()); //el código de la entidad en la base de datos debe ser igual al código del utilizado en el método
        Assert.assertEquals(usuarioModificado.getDeuda(), usuarioActualizado.getDeuda()); //el deuda de la entidad en la base de datos debe ser igual al deuda del utilizado en el método
        Assert.assertEquals(usuarioModificado.isEsAdmin(), usuarioActualizado.isEsAdmin()); //la entidad en la base de datos debe ser del mismo tipo de usuario que el utilizado en el método
        Assert.assertEquals(usuarioModificado.isPago(), usuarioActualizado.isPago()); //el pago entidad en la base de datos debe estar en el mismo estado que el del utilizado en el método
    }
    /**
     * Prueba para el método delete utilizando data como referencia
     */
    @Test
    public void deleteUsuarioTest(){
        UsuarioEntity usuarioEliminando = data.get(2); //se utiliza el último usuario creado al azar y almacenado en data 
        up.delete(usuarioEliminando.getId()); //se aplica el método para eliminarlo en la base de datos
        UsuarioEntity deleted = em.find(UsuarioEntity.class, usuarioEliminando.getId()); //se busca el usuario y se espera que este no sea encontrado
        Assert.assertNull(deleted);//verificación de una búqueda nula
    }
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
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) { //se crean tres usuarios para el análisis
            UsuarioEntity entity = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
}
