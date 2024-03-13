package com.github.vivek9237.eic.restsdk.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParser;

public class EicJsonUtils {
    private static final Gson gson = new Gson();

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
     * Converts a Map to a JSON string representing a JSONObject.
     * 
     * @param map The Map to be converted.
     * @return A JSON string representing a JSONObject.
     */
    public static String mapToJsonObjectString(Map<String, Object> map) {
        return gson.toJson(map);
    }

    /**
     * Converts a List to a JSON string representing a JSONArray.
     * 
     * @param list The List to be converted.
     * @return A JSON string representing a JSONArray.
     */
    public static String listToJsonArrayString(List<Object> list) {
        return gson.toJson(list);
    }

    /**
     * Converts a JSON string representing a JSONObject to a Java Map.
     * 
     * @param jsonObjectString The JSON string representing a JSONObject.
     * @return A Map representing the JSON object.
     */
    public static Map<String, Object> jsonObjectStringToMap(String jsonObjectString) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return gson.fromJson(jsonObjectString, type);
    }

    /**
     * Converts a JSON string representing a JSONArray to a Java List.
     * 
     * @param jsonArrayString The JSON string representing a JSONArray.
     * @return A List representing the JSON array.
     */
    public static List<Object> jsonArrayStringToList(String jsonArrayString) {
        Type type = new TypeToken<List<Object>>() {
        }.getType();
        return gson.fromJson(jsonArrayString, type);
    }

    /**
     * Converts a JSON string to a JsonObject.
     * 
     * @param jsonString The JSON string to be converted.
     * @return A JsonObject representing the JSON.
     * @throws IllegalArgumentException If the input is not a valid JSON object.
     */
    public static JsonObject jsonStringToJsonObject(String jsonString) {
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        if (jsonElement instanceof JsonObject) {
            return jsonElement.getAsJsonObject();
        } else {
            throw new IllegalArgumentException("Input is not a JSON object.");
        }
    }

    /**
     * Converts a JSON string to a JsonArray.
     * 
     * @param jsonString The JSON string to be converted.
     * @return A JsonArray representing the JSON.
     * @throws IllegalArgumentException If the input is not a valid JSON array.
     */
    public static JsonArray jsonStringToJsonArray(String jsonString) {
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        if (jsonElement instanceof JsonArray) {
            return jsonElement.getAsJsonArray();
        } else {
            throw new IllegalArgumentException("Input is not a JSON array.");
        }
    }

    /**
     * Converts a JsonObject to a JSON string.
     * 
     * @param jsonObject The JsonObject to be converted.
     * @return A JSON string representing the JsonObject.
     */
    public static String jsonObjectToJsonString(JsonObject jsonObject) {
        return jsonObject.toString();
    }

    /**
     * Converts a JsonArray to a JSON string.
     * 
     * @param jsonArray The JsonArray to be converted.
     * @return A JSON string representing the JsonArray.
     */
    public static String jsonArrayToJsonString(JsonArray jsonArray) {
        return jsonArray.toString();
    }

    /**
     * Parses a JSON file located at the specified file path and returns its
     * contents as a Map&lt;String, Object&gt;.
     * 
     * @param filePath The path to the JSON file relative to the classpath.
     * @return A Map containing the parsed JSON data, where keys are strings and
     *         values are objects.
     * @throws NullPointerException If the file path is null or if the file does not
     *                              exist.
     */
    public static Map<String, Object> parseJsonFileToMap(String filePath) {
        InputStream inputStream = EicClientUtils.class.getResourceAsStream(filePath);
        return jsonObjectStringToMap(convertInputStreamToString(inputStream));
    }

    // Method to convert InputStream to String
    private static String convertInputStreamToString(InputStream inputStream) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            // Use Java 8's Stream API to read the lines and join them into a single string
            return br.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}