package org.example.util;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass

public class PropertyUtil {
    public final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private void loadProperties() {
        try (InputStream stream = PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}