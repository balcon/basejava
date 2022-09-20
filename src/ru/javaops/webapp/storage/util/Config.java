package ru.javaops.webapp.storage.util;

import ru.javaops.webapp.storage.SqlStorage;
import ru.javaops.webapp.storage.Storage;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    private final Properties properties;
    private final Storage storage;

    private Config() {
        String path = Objects.requireNonNull(Config.class.getClassLoader().getResource("application.properties")).getPath();
        properties = new Properties();
        try (Reader reader = new FileReader(path)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException("Error read config file", e);
        }
        storage = new SqlStorage(properties.getProperty("db.url"),
                properties.getProperty("db.user"), properties.getProperty("db.password"));
    }

    public static Config get() {
        return INSTANCE;
    }

    public String getStorageTestDir() {
        return properties.getProperty("storage_test.dir");
    }

    public Storage getStorage(){
        return storage;
    }
}
