package com.saga.uni.serdes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonService {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module

    public static <T> List<T> parseJsonToList(String json, Class<T> serializableClass) throws IOException, ClassNotFoundException {
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + serializableClass.getName() + ";");
        T[] objects = JSON_MAPPER.readValue(json, arrayClass);
        return Arrays.asList(objects);
    }

    public static <T> T[] parseJsonToArray(Object jSonObject, Class<T> serializableClass) throws IOException, ClassNotFoundException {
        Class<T[]> arrayClass = (Class<T[]>) Class.forName("[L" + serializableClass.getName() + ";");
        String jsonArray = JSON_MAPPER.writeValueAsString(jSonObject);
        T[] objects = JSON_MAPPER.readValue(jsonArray, arrayClass);
        return objects;
    }

    public static <T> T parseJsonToObject(Object jSonObject, Class<T> serializableClass) {
        String jsonArray;
        T objects = null;
        try {
            jsonArray = JSON_MAPPER.writeValueAsString(jSonObject);
            objects = JSON_MAPPER.readValue(jsonArray, serializableClass);
        } catch (Exception ex) {
            Logger.getLogger(JsonService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return objects;
    }

    public static <T> T parseJsonToObject(String jSonObject, Class<T> serializableClass) {
        T objects = null;
        try {
            objects = JSON_MAPPER.readValue(jSonObject, serializableClass);
        } catch (Exception ex) {
            Logger.getLogger(JsonService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objects;
    }

    public static String toString(Object serializableObject) {
        String ERROR_JSON = "{\"message\": \"JsonProcessingException\"}";

        String jsonResponseString;
        try {
            jsonResponseString = JSON_MAPPER.writeValueAsString(serializableObject);
        } catch (JsonProcessingException ex) {
            jsonResponseString = ERROR_JSON;
        }
        return jsonResponseString;
    }

    public static ObjectNode createObject(String key, String value) {
        ObjectNode data = JSON_MAPPER.createObjectNode();
        data.put(key, value);
        return data;
    }

    public static ObjectNode createObject(String key, ObjectNode value) {
        ObjectNode data = JSON_MAPPER.createObjectNode();
        data.put(key, value);
        return data;
    }

}
