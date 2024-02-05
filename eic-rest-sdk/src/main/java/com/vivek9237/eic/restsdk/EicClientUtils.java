package com.vivek9237.eic.restsdk;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.yaml.snakeyaml.Yaml;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


public class EicClientUtils {
    public static String encodeCredentials(String username, String password) {
        // Concatenate username and password with a colon
        String credentials = username + ":" + password;
        // Encode the credentials to Base64
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        String base64Credentials = Base64.getEncoder().encodeToString(credentialsBytes);
        return base64Credentials;
    }
    
    public static EicResponse sendRequest(EicRequest eicRequest) throws IOException {
        return sendRequest(eicRequest.getUrl(), eicRequest.getMethod(), eicRequest.getHeaders(), eicRequest.getBody());
    }
    public static EicResponse sendRequest(String apiUrl, String method, Map<String, String> headers, String requestBody) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            // Set HTTP method
            connection.setRequestMethod(method);
            // Set request headers
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // Enable input/output streams
            connection.setDoOutput(true);
            // Write request body
            if (requestBody != null && !requestBody.isEmpty()) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }
            // Get response code
            int responseCode = connection.getResponseCode();
            // Get response headers
            Map<String, String> responseHeaders = new HashMap<>();
            for (Map.Entry<String, List<String>> entry : connection.getHeaderFields().entrySet()) {
                if (entry.getKey() != null) {
                    responseHeaders.put(entry.getKey(), entry.getValue().get(0));
                }
            }
            // Read the response body
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            } catch (IOException e) {
                // Handle cases where there is no input stream (e.g., for error responses)
            }
            return new EicResponse(responseCode, responseHeaders, response.toString());
        } finally {
            // Disconnect to release resources
            connection.disconnect();
        }
    }

    public static Map<String, Object> convertJsonObjectToMap(JsonObject jsonObject) {
        // Use TypeToken to get the correct Type for the Map
        Type type = new TypeToken<Map<String, Object>>() {}.getType();
        // Convert JsonObject to Map
        return new Gson().fromJson(jsonObject, type);
    }

    public static Map<String,JsonObject> parseJwt(String jwt) {
        Map<String,JsonObject> jwtMap = new HashMap<String,JsonObject>();
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        JsonObject headerJsonObj =  JsonParser.parseString(header).getAsJsonObject();
        JsonObject payloadJsonObj =  JsonParser.parseString(payload).getAsJsonObject();
        jwtMap.put("header",headerJsonObj);
        jwtMap.put("payload",payloadJsonObj);
        return jwtMap;
    }

    public static Date getIatFromJwt(String jwt) {
        String unixEpochString = parseJwt(jwt).get("payload").get("iat").toString();
        return getDateFromUnixEpoch(unixEpochString);
    }

    public static Date getDateFromUnixEpoch(String unixEpochString){
        Long unixEpoch = Long.parseLong(unixEpochString);
        Instant instant = Instant.ofEpochSecond(unixEpoch);
        Date date = Date.from(instant);
        return date;
    }
    public static String mapToString(Map<String, ?> map) {
        String mapAsString = map.keySet().stream()
            .map(key -> key + "=" + map.get(key))
            .collect(Collectors.joining(", ", "{", "}"));
        return mapAsString;
    }
    public static Date addDaysToDate(Date date, Integer daysToAdd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return calendar.getTime();
    }
    public static Boolean hasDatePassed(Date date, Integer bufferMinutes){
        long timeDifferenceMillis = date.getTime() - new Date().getTime();
        boolean isGreater = timeDifferenceMillis > (bufferMinutes * 60 * 1000);
        return isGreater;
    }
    public static Date addSecondsToDate(Date date, Integer seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
    public static String convertMapToJsonString(Map<String, Object> map) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(map);
    }
    public static String convertListToJsonString(List<Object> list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }
    public static Map<String, Object> parseYamlFile(String filePath) throws FileNotFoundException {
        //InputStream inputStream = new FileInputStream(filePath);
        InputStream inputStream = EicClientUtils.class.getResourceAsStream(filePath);
        Yaml yaml = new Yaml();
        return yaml.load(inputStream);
    }
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

    public static Boolean isStringEmpty(String value){
        Boolean isEmpty = false;
        if(value==null || value.trim().equals("")){
            isEmpty = true;
        }
        return isEmpty;
    }
}