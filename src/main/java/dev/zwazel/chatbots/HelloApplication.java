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
    private static Properties properties = null;

    public HelloApplication() {

    }

    private static void checkIfPropertyExists() {
        if (properties == null) {
            properties = new Properties();
            try (InputStream input = new FileInputStream(HelloApplication.class.getClassLoader().getResource("myproperties.properties").getFile())) {
                properties.load(input);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String key) {
        checkIfPropertyExists();

        return properties.getProperty(key);
    }
}