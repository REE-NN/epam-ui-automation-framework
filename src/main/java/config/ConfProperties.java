package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfProperties {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("conf.properties")) {

            if (is == null) {
                throw new IllegalStateException("conf.properties not found in classpath");
            }
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load conf.properties", e);
        }
    }
    private ConfProperties() {}
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
    public static String getRequired(String key) {
        String v = PROPERTIES.getProperty(key);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException("Property '" + key + "' is missing or empty in conf.properties");
        }
        return v.trim();
    }
}

