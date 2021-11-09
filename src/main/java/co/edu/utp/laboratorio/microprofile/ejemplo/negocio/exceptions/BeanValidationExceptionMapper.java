package co.edu.utp.laboratorio.microprofile.ejemplo.negocio.exceptions;

import javax.json.Json;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Collections;
import java.util.Set;

@Provider
public class BeanValidationExceptionMapper
        implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        String message = constraintViolations.stream()
                .map(constraintViolation -> constraintViolation.getMessage()).findFirst().orElse("");
        return Response
                .status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON_TYPE)
                .entity(
                        Json.createBuilderFactory(Collections.emptyMap())
                                .createObjectBuilder()
                                .add("error",message)
                                .build()
                )
                .build();
    }
}