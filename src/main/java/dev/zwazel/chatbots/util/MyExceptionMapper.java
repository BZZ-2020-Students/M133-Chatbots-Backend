package dev.zwazel.chatbots.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{" +
                        "\"error\":\"" + prepareMessageSimple(exception) + "\"" +
                        "\"errorDetailed\":\"" + prepareMessageDetailed(exception) + "\"" +
                        "}")
                .type("application/json")
                .build();
    }

    private String prepareMessageSimple(ConstraintViolationException exception) {
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            sb.append(violation.getMessage()).append("\n");
        }
        return sb.toString();
    }

    private String prepareMessageDetailed(ConstraintViolationException exception) {
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            msg.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("\n");
        }
        return msg.toString();
    }
}