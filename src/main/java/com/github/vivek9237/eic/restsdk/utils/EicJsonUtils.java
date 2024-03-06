package com.github.vivek9237.eic.restsdk.utils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class EicJsonUtils {
    /**
     * Converts a JsonObject to a Map.
     * 
     * @param jsonObject The JsonObject to convert.
     * @return The resulting Map.
     */
    public static Map<String, Object> convertJsonObjectToMap(JsonObject jsonObject) {
        // Use TypeToken to get the correct Type for the Map
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        // Convert JsonObject to Map
        return new Gson().fromJson(jsonObject, type);
    }

    /**
     * Converts a Map to a String representation.
     * 
     * @param map The Map to convert.
     * @return The String representation of the Map.
     */
    public static String mapToString(Map<String, ?> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }

    /**
     * Converts a Map to a JSON string.
     * 
     * @param map The Map to convert.
     * @return The JSON string representation of the Map.
     * @throws JsonProcessingException If an error occurs during JSON processing.
     */
    public static String convertMapToJsonString(Map<String, Object> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }

    /**
     * Converts a List to a JSON string.
     * 
     * @param list The List to convert.
     * @return The JSON string representation of the List.
     * @throws JsonProcessingException If an error occurs during JSON processing.
     */
    public static String convertListToJsonString(List<Object> list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }

    /**
     * Converts a JSON string to a Map.
     * 
     * @param jsonString The JSON string to convert.
     * @return The resulting Map.
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            return convertJsonNodeToMap(jsonNode);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, Object> convertJsonNodeToMap(JsonNode jsonNode) {
        Map<String, Object> resultMap = new HashMap<>();
        jsonNode.fields().forEachRemaining(entry -> {
            String key = entry.getKey();
            JsonNode value = entry.getValue();
            if (value.isObject()) {
                // Recursively convert nested objects
                resultMap.put(key, convertJsonNodeToMap(value));
            } else if (value.isArray()) {
                // Convert arrays to List
                resultMap.put(key, convertJsonNodeToList(value));
            } else {
                // Handle other types
                resultMap.put(key, value.asText());
            }
        });

        return resultMap;
    }

    private static List<Object> convertJsonNodeToList(JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Object> resultList = objectMapper.convertValue(jsonNode, List.class);
        // Handle nested structures within the list
        for (int i = 0; i < resultList.size(); i++) {
            Object element = resultList.get(i);
            if (element instanceof JsonNode) {
                resultList.set(i, convertJsonNodeToMap((JsonNode) element));
            }
        }
        return resultList;
    }
}