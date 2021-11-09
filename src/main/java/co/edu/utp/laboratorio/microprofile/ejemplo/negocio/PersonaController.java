package co.edu.utp.laboratorio.microprofile.ejemplo.negocio;

import co.edu.utp.laboratorio.microprofile.ejemplo.negocio.exceptions.JSONWebApplicationException;
import co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.entidades.Persona;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.Json;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Path("/persona")
@Singleton
public class PersonaController {
    @Inject
    private PersonaBO personaBO;

    @GET
    @Path("{dni}")
    @Produces({MediaType.APPLICATION_JSON})
    public Persona find(@PathParam("dni") String dni){
        Objects.requireNonNull(dni);
        Persona persona = personaBO.find(dni);
        if (persona == null){
            throw new JSONWebApplicationException(String.format("Mensaje %s no existe",dni), Response.Status.NOT_FOUND);
        }
        return persona;
    }

    @PUT
    @Path("{dni}")
    @Produces({MediaType.APPLICATION_JSON})
    public Persona update(@PathParam("dni") String dni,@Valid Persona persona){
        find(dni);
        if( !dni.equals(persona.getDni()) ){
            throw new JSONWebApplicationException(String.format("No se puede modificar el dni",dni), Response.Status.CONFLICT);
        }
        personaBO.update(persona);
        return persona;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Persona> findAll(){
        return personaBO.findAll();
    }

    @DELETE
    @Path("{dni}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("dni")String dni){
        Persona persona = find(dni);
        personaBO.delete(persona);
        return Response.noContent().build();
    }

    @POST
    @RolesAllowed({"user"})
    public Response save(@Valid Persona persona){
        if( personaBO.find(persona.getDni()) != null ){
            throw new JSONWebApplicationException(
                    String.format("La persona con dni %s ya existe",persona.getDni()),Response.Status.CONFLICT);
        }
        personaBO.create(persona);
        return Response.status(Response.Status.CREATED).entity(persona).build();
    }



}
