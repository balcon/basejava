package ru.javaops.webapp.storage.util;

import com.google.gson.*;
import ru.javaops.webapp.model.AbstractSection;

import java.lang.reflect.Type;

public class JsonSectionAdapter implements JsonSerializer<AbstractSection>, JsonDeserializer<AbstractSection> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public JsonElement serialize(AbstractSection section, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, section.getClass().getName());
        JsonElement elem = context.serialize(section);
        retValue.add(INSTANCE, elem);
        return retValue;
    }

    @Override
    public AbstractSection deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();

        try {
            Class<?> clazz = Class.forName(className);
            return context.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}