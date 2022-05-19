package dev.zwazel.chatbots.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;

import java.util.List;

/**
 * Class that converts a Java object to a JSON string.
 *
 * @author Zwazel
 * @since 0.3
 */
public class ToJson<T> {
    /**
     * Converts a Java object to a JSON string, with a filter.
     *
     * @param t              The object to convert.
     * @param filterProvider The filter to use.
     * @param <T>            The type of the object.
     * @return The JSON string.
     * @throws JsonProcessingException If the object cannot be converted.
     * @author Zwazel
     * @since 0.3
     */
    public static <T> String toJson(T t, FilterProvider filterProvider) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writer(filterProvider)
                .writeValueAsString(t);
    }

    /**
     * Converts a Java object to a JSON string.
     *
     * @param t   The object to convert.
     * @param <T> The type of the object.
     * @return The JSON string.
     * @throws JsonProcessingException If the object cannot be converted.
     * @author Zwazel
     * @since 0.3
     */
    public static <T> String toJson(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writer()
                .writeValueAsString(t);
    }

    /**
     * Converts a List of Java objects to a JSON string.
     *
     * @param objects The objects to convert.
     * @param <T>     The type of the objects.
     * @return The JSON string.
     * @throws JsonProcessingException If the object cannot be converted.
     * @author Zwazel
     * @since 0.3
     */
    public static <T> String arrayToJson(List<T> objects) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writer()
                .writeValueAsString(objects);
    }

    /**
     * Converts an Iterable of Java objects to a JSON string.
     *
     * @param objects The objects to convert.
     * @param <T>     The type of the objects.
     * @return The JSON string.
     * @throws JsonProcessingException If the object cannot be converted.
     * @author Zwazel
     * @since 0.3
     */
    public static <T> String arrayToJson(Iterable<T> objects) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writer()
                .writeValueAsString(objects);
    }

    /**
     * Converts a List of Java objects to a JSON string, with a filter.
     *
     * @param objects        The objects to convert.
     * @param filterProvider The filter to use.
     * @param <T>            The type of the objects.
     * @return The JSON string.
     * @throws JsonProcessingException If the object cannot be converted.
     * @author Zwazel
     * @since 0.3
     */
    public static <T> String arrayToJson(List<T> objects, FilterProvider filterProvider) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writer()
                .with(filterProvider)
                .writeValueAsString(objects);
    }

    /**
     * Converts an Iterable of Java objects to a JSON string with a filter.
     *
     * @param objects        The objects to convert.
     * @param <T>            The type of the objects.
     * @param filterProvider The filter to use.
     * @return The JSON string.
     * @throws JsonProcessingException If the object cannot be converted.
     * @author Zwazel
     * @since 0.3
     */
    public static <T> String arrayToJson(Iterable<T> objects, FilterProvider filterProvider) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper
                .writer()
                .with(filterProvider)
                .writeValueAsString(objects);
    }
}
