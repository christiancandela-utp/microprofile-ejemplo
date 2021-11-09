package co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.configuracion;

import co.edu.utp.laboratorio.microprofile.ejemplo.negocio.security.ClearHash;
import co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades.Persona;
import co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades.Rol;
import co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades.User;

import javax.annotation.PostConstruct;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@DataSourceDefinition(

        name="java:app/ejemplo/mysql",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        portNumber = 3306,
        //portNumber = "${db.port}"., //
        url = "${ENV=url}",//
        user = "${ENV=user}", //
        password = "${ENV=password}", //
        initialPoolSize = 2,
        minPoolSize = 2,
        maxPoolSize = 10,
        properties = {
                "useSSL=false",
                "useInformationSchema=true",
                "nullCatalogMeansCurrent=true",
                "nullNamePatternMatchesAll=false"
        }

//        name="java:app/ejemplo/mysql",
//        className = "com.microsoft.sqlserver.jdbc.SQLServerXADataSource",
//        serverName = "localhost",
//        portNumber = 1433,
//        user= "sa",
//        password = "OcP2020123",
//        databaseName = "tempdb",
//        initialPoolSize = 2,
//        minPoolSize = 2,
//        maxPoolSize = 10,
//        properties = {
//                "useSSL=false",
//                "useInformationSchema=true",
//                "nullCatalogMeansCurrent=true",
//                "nullNamePatternMatchesAll=false"
//        }
)
@Singleton
@Startup
public class FuenteDatos {
        @PersistenceContext
        private EntityManager entityManager;
        @Inject
        private ClearHash customHash;

        @PostConstruct
        public void inicializar() {
                if( entityManager.createQuery("select u from User u order by u.username", User.class).getResultList().isEmpty() ){
                        Rol all = createRol("all");
                        Rol rolUser = createRol("user");
                        createUser("root","12345", Arrays.asList(all,rolUser));
                        createUser("user","user",Arrays.asList(rolUser));
                        createUser("nn","1234",Arrays.asList(rolUser));
                        //createPerson("1234","Juan Perez",Arrays.asList("3145678475","3163452345"));
                }
        }

        private void createPerson(String dni, String name, List<String> phones) {
                Persona persona = new Persona();
                persona.setDni(dni);
                persona.setNombre(name);
                persona.setTelefonos(phones);
                entityManager.persist(persona);
        }

        private Rol createRol(String name){
                Rol rol = new Rol();
                rol.setName(name);
                entityManager.persist(rol);
                return rol;
        }

        private User createUser(String name, String password, List<Rol> roles){
                User user = new User();
                user.setUsername(name);
                user.setPassword(customHash.generate(password.toCharArray()));
                user.setRols(roles);
                entityManager.persist(user);
                return user;
        }
}
