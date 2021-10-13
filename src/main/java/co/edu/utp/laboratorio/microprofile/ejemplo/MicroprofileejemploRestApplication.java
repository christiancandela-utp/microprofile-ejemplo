package co.edu.utp.laboratorio.microprofile.ejemplo;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@DeclareRoles({"user"})
@ApplicationPath("/data")
@ApplicationScoped
public class MicroprofileejemploRestApplication extends Application {
}
