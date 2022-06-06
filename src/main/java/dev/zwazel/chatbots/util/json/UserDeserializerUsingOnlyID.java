package dev.zwazel.chatbots.util.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import dev.zwazel.chatbots.classes.dao.UserDao;
import dev.zwazel.chatbots.classes.model.User;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;

public class UserDeserializerUsingOnlyID extends JsonDeserializer<User> {
    @Override
    public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String userID = p.readValueAs(String.class);

        UserDao userDao = new UserDao();
        User user = userDao.findById(userID);

        if (user == null) {
            throw new NotFoundException("User not found");
        }

        return user;
    }
}
