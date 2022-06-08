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
                        "\"errorSimple\":" + prepareMessageSimple(exception) +
                        ",\"errorDetailed\":" + prepareMessageDetailed(exception) +
                        "}")
                .type("application/json")
                .build();
    }

    private String prepareMessageSimple(ConstraintViolationException exception) {
        StringBuilder sb = new StringBuilder("[");
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            sb.append("{\"error\":\"").append(violation.getMessage()).append("\"},");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.append("]").toString();
    }

    private String prepareMessageDetailed(ConstraintViolationException exception) {
        StringBuilder msg = new StringBuilder("[");
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            msg.append("{\"property\":\"").append(violation.getPropertyPath()).append("\",\"message\":\"").append(violation.getMessage()).append("\"},");
        }
        msg.deleteCharAt(msg.length() - 1);
        return msg.append("]").toString();
    }
}