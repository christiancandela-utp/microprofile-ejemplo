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
import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@AutoApplySession
@ApplicationScoped
public class TokenAuthtentication implements HttpAuthenticationMechanism {

    private static final String TOKEN_CONSTANT = "Bearer ";
    private static final int TOKEN_CONSTANT_LENGTH = TOKEN_CONSTANT.length();

    @Inject
    private IdentityStoreHandler identityStoreHandler;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {
        if( httpMessageContext.isAuthenticationRequest() && httpMessageContext.getAuthParameters().isNewAuthentication() ){
            CredentialValidationResult result = identityStoreHandler.validate(
                    httpMessageContext.getAuthParameters().getCredential() );
            if( result == INVALID_RESULT ){
                return AuthenticationStatus.SEND_FAILURE;
            }

            httpMessageContext.getResponse().setHeader(HttpHeaders.AUTHORIZATION,
                    TOKEN_CONSTANT+TokenUtil.create(result.getCallerPrincipal().getName(),result.getCallerGroups()));

            return httpMessageContext.notifyContainerAboutLogin( result );
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        CredentialValidationResult result = token == null ? INVALID_RESULT : TokenUtil.parseToken( token.substring(
                TOKEN_CONSTANT_LENGTH
        ) );

        if( result != INVALID_RESULT ){
            httpMessageContext.getResponse().setHeader(HttpHeaders.AUTHORIZATION,token);
            return httpMessageContext.notifyContainerAboutLogin(result.getCallerPrincipal(),result.getCallerGroups());
        }
        return httpMessageContext.doNothing();
    }


}
