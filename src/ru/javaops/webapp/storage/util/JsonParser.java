package ru.javaops.webapp.storage.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javaops.webapp.model.AbstractSection;
import ru.javaops.webapp.model.Resume;

import java.io.Reader;
import java.io.Writer;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonAbstractSectionAdapter())
            .create();

    public static void write(Resume resume, Writer writer) {
        GSON.toJson(resume, writer);
    }

    public static Resume read(Reader reader) {
        return GSON.fromJson(reader, Resume.class);
    }

}
