package dev.zwazel.chatbots;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Application class for the web application with some configurations.
 *
 * @author Zwazel
 * @since 0.1
 */
@ApplicationPath("/api")
public class HelloApplication extends Application {
    /**
     * The default JWT Issuer
     *
     * @since 0.1
     */
    public static final String defaultConfJwtIssuer = "zwazels-chatbot";

    /**
     * The default JWT Secret
     *
     * @since 0.1
     */
    public static final String defaultConfJwtSecret = "this-secret-is-a-very-secure-secret";

    /**
     * The default JWT Name
     *
     * @since 0.1
     */
    public static final String defaultConfJwtName = "zwazel-jwt-chatbot";

    /**
     * The default JWT Expiration Time
     *
     * @since 0.2
     */
    public static final String defaultCorsAllowedOrigins = "http://localhost:3000";

    /**
     * The properties of this application
     *
     * @since 0.1
     */
    private static Properties properties = null;

    /**
     * Default constructor
     *
     * @author Zwazel
     * @since 0.1
     */
    public HelloApplication() {

    }

    /**
     * Check if the properties are loaded, if not, load them. If we can't find the properties file, we will use the default values.
     * Environment variables will override the default values if they exist.
     *
     * @author Zwazel
     * @since 0.1
     */
    private static void checkIfPropertyExists() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream input = new FileInputStream(Objects.requireNonNull(HelloApplication.class.getClassLoader().getResource("testing/config.properties")).getFile())) {
                properties.load(input);
            } catch (IOException | NullPointerException e) {
                System.out.println("Could not load config.properties, using default values");

                properties.setProperty("jwt.secret", defaultConfJwtSecret);
                properties.setProperty("jwt.issuer", defaultConfJwtIssuer);
                properties.setProperty("jwt.name", defaultConfJwtName);
            }

            Map<String, String> env = System.getenv();
            for (Map.Entry<String, String> entry : env.entrySet()) {
                properties.setProperty(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * gets a specific property as String, if the property doesn't exist, it will return the default value
     *
     * @param key          the key of the property
     * @param defaultValue the default value if the property doesn't exist
     * @return the value of the property
     * @author Zwazel
     * @since 0.1
     */
    public static String getProperty(String key, String defaultValue) {
        checkIfPropertyExists();

        return properties.getProperty(key, defaultValue);
    }

    /**
     * gets a specific property as String, if the property doesn't exist, it will return null
     *
     * @param key the key of the property
     * @return the value of the property
     * @author Zwazel
     * @since 0.1
     */
    public static String getProperty(String key) {
        checkIfPropertyExists();

        return properties.getProperty(key);
    }
}