package config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfProperties {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadIfPresent("conf.properties");
        loadIfPresent("secrets.properties");
    }

    private static void loadIfPresent(String name) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(name)) {
            if (is == null) return; // нет файла — просто пропускаем
            PROPERTIES.load(new InputStreamReader(is, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + name, e);
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

