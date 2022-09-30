package ru.javaops.webapp.storage;

import ru.javaops.webapp.storage.serializers.JsonStreamSerializer;
import ru.javaops.webapp.storage.util.Config;

public class JsonStreamPathStorageTest extends AbstractFileSystemStorageTest {
    public JsonStreamPathStorageTest() {
        super(new FileStorage(Config.get().getStorageTestDir(), new JsonStreamSerializer()));
    }
}