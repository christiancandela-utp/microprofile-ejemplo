package co.edu.utp.laboratorio.microprofile.ejemplo.negocio.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.security.enterprise.identitystore.IdentityStoreHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.util.HashSet;

import static java.util.Arrays.asList;

@AutoApplySession
@ApplicationScoped
public class TokenAuthtentication implements HttpAuthenticationMechanism {

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        if( httpMessageContext.isAuthenticationRequest() && httpMessageContext.getAuthParameters().isNewAuthentication() ){
            CredentialValidationResult result = identityStoreHandler.validate(
                    httpMessageContext.getAuthParameters().getCredential() );
            if( result == CredentialValidationResult.INVALID_RESULT ){
                return AuthenticationStatus.SEND_FAILURE;
            }

            httpMessageContext.getResponse().setHeader(HttpHeaders.AUTHORIZATION,"Bearer 123aasdasd121212323");

            return httpMessageContext.notifyContainerAboutLogin( result );
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if( token != null && isValid(token) ){
            httpMessageContext.getResponse().setHeader(HttpHeaders.AUTHORIZATION,"Bearer 123aasdasd121212323");
            return httpMessageContext.notifyContainerAboutLogin("prueba",new HashSet<>(asList("user")));
        }
        return httpMessageContext.doNothing();
    }

    private boolean isValid(String token) {
        return ("Bearer 123aasdasd121212323").equals(token);
    }
}
