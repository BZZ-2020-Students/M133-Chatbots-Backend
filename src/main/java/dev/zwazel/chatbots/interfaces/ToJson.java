package dev.zwazel.chatbots.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zwazel.chatbots.classes.model.User;

import java.util.List;

public class ToJson {
    public static <T> String toJson(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(t);
    }

    public static <T> String arrayToJson(List<T> objects) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(objects);
    }

    public static <T> String arrayToJson(Iterable<T> objects) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(objects);
    }
}
