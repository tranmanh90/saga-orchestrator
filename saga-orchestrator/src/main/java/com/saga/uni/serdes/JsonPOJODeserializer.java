package com.saga.uni.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Deserializer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class JsonPOJODeserializer<T> implements Deserializer<T> {

    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> tClass;

    public JsonPOJODeserializer(Class<T> clazz){
        this.tClass = clazz;
    }

    @SneakyThrows
    @Override
    public T deserialize(final String topic, final byte[] bytes) {
        String s = new String(bytes, StandardCharsets.UTF_8);
        Gson gson = new Gson();
        if (bytes == null) {
            return null;
        }

        T data;
        try {
            if (s.contains("\\")){
                data = gson.fromJson(s.replaceAll("\\\\", "").substring(1, s.replaceAll("\\\\", "").length() - 1), tClass);
            } else {
                data = objectMapper.readValue(s.replaceAll("\\\\", ""), tClass);
            }
        } catch (final Exception e) {
            logger.warning("Failed to convert " + s + " to " + tClass.getName());
            return null;
        }
        return data;
    }

    @Override
    public void close() {

    }

    private Type findMyType(Class<?> plainClass, Type genericClass) {
        Class<?> plainSuper = plainClass.getSuperclass();
        Type genericSuper = plainClass.getGenericSuperclass();

        Type t;

        if (plainSuper == JsonPOJODeserializer.class) {
            t = ((ParameterizedType) genericSuper).getActualTypeArguments()[0];
        } else {
            t = findMyType(plainSuper, genericSuper);
        }

        if (t instanceof TypeVariable<?>) {
            TypeVariable<?>[] vars = plainClass.getTypeParameters();

            for (int i = 0; i < vars.length; ++i) {
                if (t == vars[i]) {
                    t = ((ParameterizedType) genericClass).getActualTypeArguments()[i];
                    break;
                }
            }
        }

        return t;
    }
}