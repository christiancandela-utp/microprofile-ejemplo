package co.edu.utp.laboratorio.microprofile.ejemplo;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 */
@ApplicationPath("/data")
@ApplicationScoped
public class MicroprofileejemploRestApplication extends Application {
}
