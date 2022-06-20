package dev.zwazel.chatbots.util;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 * Exception mapper for the {@link ConstraintViolationException}.
 *
 * @author Zwazel
 * @since 1.3.0
 */
@Provider
public class MyExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    /**
     * Maps the {@link ConstraintViolationException} to a {@link Response}.
     *
     * @param exception the exception to map to a response.
     * @return the response.
     * @author Zwazel
     * @since 1.3.0
     */
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

    /**
     * Prepares the message for the simple error.
     *
     * @param exception the exception to prepare the message for.
     * @return the message.
     * @author Zwazel
     * @since 1.3.0
     */
    private String prepareMessageSimple(ConstraintViolationException exception) {
        StringBuilder sb = new StringBuilder("[");
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            sb.append("{\"error\":\"").append(violation.getMessage()).append("\"},");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.append("]").toString();
    }

    /**
     * Prepares the message for the detailed error.
     *
     * @param exception the exception to prepare the message for.
     * @return the message.
     * @author Zwazel
     * @since 1.3.0
     */
    private String prepareMessageDetailed(ConstraintViolationException exception) {
        StringBuilder msg = new StringBuilder("[");
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            msg.append("{\"property\":\"").append(violation.getPropertyPath()).append("\",\"message\":\"").append(violation.getMessage()).append("\"},");
        }
        msg.deleteCharAt(msg.length() - 1);
        return msg.append("]").toString();
    }
}