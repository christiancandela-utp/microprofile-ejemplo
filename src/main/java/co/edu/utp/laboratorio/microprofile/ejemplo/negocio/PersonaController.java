package co.edu.utp.laboratorio.microprofile.ejemplo.negocio;

import co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades.Persona;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.Json;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/persona")
@Singleton
public class PersonaController {
    @Inject
    private PersonaBO personaBO;

    @GET
    @Path("{dni}")
    @Produces({MediaType.APPLICATION_JSON})
    public Persona find(@PathParam("dni") String dni){
        Persona persona = personaBO.find(dni);
        if (persona == null){
            throw new WebApplicationException(String.format("Mensaje %s no existe",dni), Response.Status.NOT_FOUND);
        }
        return persona;
    }

    @POST
    public Response save(Persona persona){
        if( personaBO.find(persona.getDni()) != null ){
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(
                                    Json.createBuilderFactory(Collections.emptyMap())
                                    .createObjectBuilder()
                                    .add("error",String.format("La persona con dni %s ya existe",persona.getDni()))
                                    .build()
                    ).build()
            );
        }
        personaBO.create(persona);
        return Response.status(Response.Status.CREATED).entity(persona).build();
    }
}
