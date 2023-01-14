package com.tass.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class JsonHelper {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public static String toString(Object obj) {

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T getObject(String data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(Map<String, Object> data, Class<T> clazz) {
        try {
            return mapper.convertValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObjectFromMap(Map<String, String> data, Class<T> clazz) {
        try {
            return mapper.convertValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(byte[] data, Class<T> clazz) {
        try {
            return mapper.readValue(data, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T getObject(InputStream inputStream, Class<T> clazz) {
        try {
            return mapper.readValue(inputStream, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> getMap(String jsonData) {
        try {
            return mapper.readValue(jsonData, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static Map<String, Object> getMapWithException(String jsonData) throws Exception {
        return mapper.readValue(jsonData, HashMap.class);
    }

    public static Map<String, Object> getMap(byte[] body) {
        try {
            return mapper.readValue(body, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static Map<String, String> getStringMap(InputStream inputStream) {
        try {
            return mapper.readValue(inputStream, Map.class);
        } catch (Exception e) {
//            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static void main(String[] args) {
    }
}
