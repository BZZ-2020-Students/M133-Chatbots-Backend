package dev.zwazel.chatbots;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@ApplicationPath("/api")
@ApplicationScoped
@DeclareRoles({"USER", "ADMIN"})
public class HelloApplication extends Application {
    public static final String defaultConfJwtIssuer = "zwazels-chatbot";
    public static final String defaultConfJwtSecret = "this-secret-is-a-very-secure-secret";
    public static final String defaultConfJwtName = "zwazel-jwt-chatbot";
    private static Properties properties = null;

    public HelloApplication() {

    }

    private static void checkIfPropertyExists() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream input = new FileInputStream(HelloApplication.class.getClassLoader().getResource("config.properties").getFile())) {
                properties.load(input);
            } catch (IOException e) {
                System.err.println("Could not load config.properties, using default values");

                properties.setProperty("jwt.secret", defaultConfJwtSecret);
                properties.setProperty("jwt.issuer", defaultConfJwtIssuer);
                properties.setProperty("jwt.name", defaultConfJwtName);
            }
        }
    }

    public static String getProperty(String key, String defaultValue) {
        checkIfPropertyExists();

        return properties.getProperty(key, defaultValue);
    }

    public static String getProperty(String key) {
        checkIfPropertyExists();

        return properties.getProperty(key);
    }
}