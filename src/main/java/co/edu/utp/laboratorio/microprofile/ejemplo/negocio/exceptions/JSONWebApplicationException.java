package co.edu.utp.laboratorio.microprofile.ejemplo.negocio.exceptions;

import javax.json.Json;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collections;

public class JSONWebApplicationException extends WebApplicationException {
    public JSONWebApplicationException(String message, Response.Status status) {
        super(
                Response.status(status)
                        .entity(
                                Json.createBuilderFactory(Collections.emptyMap())
                                        .createObjectBuilder()
                                        .add("error",message)
                                        .build()
                        ).build()
        );
    }
}
