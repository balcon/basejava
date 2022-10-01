package ru.javaops.webapp.storage.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.javaops.webapp.model.AbstractSection;
import ru.javaops.webapp.model.Resume;

import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter())
            .registerTypeAdapter(LocalDate.class, new JsonLocalDateAdapter())
            .create();

    public static void write(Resume resume, Writer writer) {
        GSON.toJson(resume, writer);
    }

    public static String write(AbstractSection section) {
        return GSON.toJson(section, AbstractSection.class);
    }

    public static Resume read(Reader reader) {
        return GSON.fromJson(reader, Resume.class);
    }

    public static AbstractSection read(String value) {
        return GSON.fromJson(value, AbstractSection.class);
    }

}
