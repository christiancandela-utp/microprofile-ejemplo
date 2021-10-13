package co.edu.utp.laboratorio.microprofile.ejemplo;

import co.edu.utp.laboratorio.microprofile.ejemplo.negocio.security.ClearHash;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@DatabaseIdentityStoreDefinition(
        dataSourceLookup = "${'java:app/ejemplo/mysql'}",
        callerQuery = "#{'select password from USER where username = ?'}",
        groupsQuery = "select rol_name from USER_ROL where username = ?",
        //hashAlgorithm = Pbkdf2PasswordHash.class,
        hashAlgorithm = ClearHash.class,
        priorityExpression = "#{100}"
//        ,
//        hashAlgorithmParameters = {
//                "Pbkdf2PasswordHash.Iterations=3072",
//                "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA512",
//                "Pbkdf2PasswordHash.SaltSizeBytes=64",
//        }
)
@DeclareRoles({"user"})
@ApplicationPath("/data")
@ApplicationScoped
public class MicroprofileejemploRestApplication extends Application {
}
