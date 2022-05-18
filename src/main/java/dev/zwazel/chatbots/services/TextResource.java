package dev.zwazel.chatbots.services;

import dev.zwazel.chatbots.classes.dao.TextDao;
import dev.zwazel.chatbots.classes.model.Text;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * Resource class for Text objects.
 *
 * @author Zwazel
 * @since 0.3
 */
@Path("/text")
public class TextResource {
    /**
     * Returns a Text object by its id.
     *
     * @param id The id of the Text object.
     * @return A Text object.
     */
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getText(@PathParam("id") String id) {
        TextDao textDao = new TextDao();
        Text text = textDao.find(id);

        if (text == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(text).build();
    }

    /**
     * Returns all Text objects.
     *
     * @return A list of Text objects.
     * @author Zwazel
     * @since 0.3
     */
    @GET
    @Path("/list")
    @Produces("application/json")
    public Response getTexts() {
        TextDao textDao = new TextDao();
        return Response.ok(textDao.findAll()).build();
    }
}
