package com.saga.uni.serdes;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

public class SerdesFactory {
    public static <T> Serde<T> getSerde(Class<T> type){
        final Serializer<T> serializer = new JsonSerializer<>();
        final Deserializer<T> deserializer = new JsonDeserializer<>(type);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}