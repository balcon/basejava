package ru.javaops.webapp.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_PATH =
            Config.class.getClassLoader().getResource("application.properties").getPath();
    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(new FileReader(PROPERTIES_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Error read config file", e);
        }
    }

    private Config() {
    }

    private String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
