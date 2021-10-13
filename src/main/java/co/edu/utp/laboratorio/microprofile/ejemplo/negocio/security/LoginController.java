package co.edu.utp.laboratorio.microprofile.ejemplo.negocio.security;

import co.edu.utp.laboratorio.microprofile.ejemplo.negocio.exceptions.JSONWebApplicationException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.Json;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.util.Collections;

@Path("/login")
@Singleton
public class LoginController {

    @Inject
    private SecurityContext securityContext;
    @Context
    private HttpServletRequest request;
    @Context
    private HttpServletResponse response;

    @POST
    public Response login(LoginDTO loginDTO){
        String token = "";


        UsernamePasswordCredential credential = new UsernamePasswordCredential(
                loginDTO.getUsername(),new Password(loginDTO.getPassword())
        );
        AuthenticationStatus status = securityContext.authenticate(request,response,
                AuthenticationParameters.withParams().credential(credential).newAuthentication(true));

        if( status != AuthenticationStatus.SUCCESS ){
            throw new JSONWebApplicationException("Usuario o clave incorrecta", Response.Status.UNAUTHORIZED);
        }

        token = response.getHeader(HttpHeaders.AUTHORIZATION);
        // TODO obtener el token
        return Response.ok(
                Json.createBuilderFactory(Collections.emptyMap())
                        .createObjectBuilder()
                        .add(HttpHeaders.AUTHORIZATION,token)
        ).build();
    }
}
