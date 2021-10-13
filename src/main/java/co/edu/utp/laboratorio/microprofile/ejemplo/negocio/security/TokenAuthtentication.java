package co.edu.utp.laboratorio.microprofile.ejemplo.negocio.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

import static java.util.Arrays.asList;

@AutoApplySession
@ApplicationScoped
public class TokenAuthtentication implements HttpAuthenticationMechanism {

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        String token = request.getHeader("JWT");
        if( token != null && isValid(token) ){
            return httpMessageContext.notifyContainerAboutLogin("prueba",new HashSet<>(asList("user")));
        }
        return httpMessageContext.doNothing();
    }

    private boolean isValid(String token) {
        return "JWT:12345".equals(token);
    }
}
