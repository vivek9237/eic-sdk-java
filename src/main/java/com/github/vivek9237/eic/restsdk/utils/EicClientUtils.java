package com.github.vivek9237.eic.restsdk.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.Instant;

import com.github.vivek9237.eic.restsdk.core.EicRequest;
import com.github.vivek9237.eic.restsdk.core.EicResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EicClientUtils {
    /**
     * Encodes the given username and password credentials into Base64 format.
     * 
     * @param username The username.
     * @param password The password.
     * @return The Base64 encoded credentials.
     */
    public static String encodeCredentials(String username, String password) {
        // Concatenate username and password with a colon
        String credentials = username + ":" + password;
        // Encode the credentials to Base64
        byte[] credentialsBytes = credentials.getBytes(StandardCharsets.UTF_8);
        String base64Credentials = Base64.getEncoder().encodeToString(credentialsBytes);
        return base64Credentials;
    }

    /**
     * Sends a HTTP request based on the provided EicRequest object.
     * 
     * @param eicRequest The EicRequest object containing request details.
     * @return The EicResponse object containing response details.
     * @throws IOException If an I/O error occurs.
     */
    public static EicResponse sendRequest(EicRequest eicRequest) throws IOException {
        return sendRequest(eicRequest.getUrl(), eicRequest.getMethod(), eicRequest.getHeaders(), eicRequest.getBody());
    }

    /**
     * Sends a HTTP request to the specified API URL using the provided method,
     * headers, and request body.
     * 
     * @param apiUrl      The URL of the API endpoint.
     * @param method      The HTTP method (e.g., GET, POST).
     * @param headers     A map containing request headers.
     * @param requestBody The request body.
     * @return The EicResponse object containing response details.
     * @throws IOException If an I/O error occurs.
     */
    public static EicResponse sendRequest(String apiUrl, String method, Map<String, String> headers, String requestBody)
            throws IOException {
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
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
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

    /**
     * Parses a JWT token and returns the header and payload as separate
     * JsonObjects.
     * 
     * @param jwt The JWT token to parse.
     * @return A Map containing "header" and "payload" JsonObjects.
     */
    public static Map<String, JsonObject> parseJwt(String jwt) {
        Map<String, JsonObject> jwtMap = new HashMap<String, JsonObject>();
        String[] chunks = jwt.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        JsonObject headerJsonObj = JsonParser.parseString(header).getAsJsonObject();
        JsonObject payloadJsonObj = JsonParser.parseString(payload).getAsJsonObject();
        jwtMap.put("header", headerJsonObj);
        jwtMap.put("payload", payloadJsonObj);
        return jwtMap;
    }

    /**
     * Extracts the "iat" (issued at) claim from a JWT token.
     * 
     * @param jwt The JWT token.
     * @return The issued at timestamp as a Date object.
     */
    public static Date getIatFromJwt(String jwt) {
        String unixEpochString = parseJwt(jwt).get("payload").get("iat").toString();
        return getDateFromUnixEpoch(unixEpochString);
    }

    /**
     * Converts a Unix epoch string to a Date object.
     * 
     * @param unixEpochString The Unix epoch string.
     * @return The corresponding Date object.
     */
    public static Date getDateFromUnixEpoch(String unixEpochString) {
        Long unixEpoch = Long.parseLong(unixEpochString);
        Instant instant = Instant.ofEpochSecond(unixEpoch);
        Date date = Date.from(instant);
        return date;
    }

    /**
     * Adds a specified number of days to a Date object.
     * 
     * @param date      The original Date.
     * @param daysToAdd The number of days to add.
     * @return The resulting Date after adding the specified days.
     */
    public static Date addDaysToDate(Date date, Integer daysToAdd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        return calendar.getTime();
    }

    /**
     * Determines if a Date has passed relative to the current time.
     * 
     * @param date          The Date to compare.
     * @param bufferMinutes The buffer time in minutes.
     * @return true if the Date has passed; otherwise false.
     */
    public static Boolean hasDatePassed(Date date, Integer bufferMinutes) {
        long timeDifferenceMillis = date.getTime() - new Date().getTime();
        boolean isGreater = timeDifferenceMillis > (bufferMinutes * 60 * 1000);
        return isGreater;
    }

    /**
     * Adds a specified number of seconds to a Date object.
     * 
     * @param date    The original Date.
     * @param seconds The number of seconds to add.
     * @return The resulting Date after adding the specified seconds.
     */
    public static Date addSecondsToDate(Date date, Integer seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * Checks if a string is empty or null.
     * 
     * @param value The string to check.
     * @return true if the string is empty or null; otherwise false.
     */
    public static Boolean isStringEmpty(String value) {
        Boolean isEmpty = false;
        if (value == null || value.trim().equals("")) {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     * Converts a Map of parameters to a query string.
     * 
     * @param params The Map of parameters.
     * @return The resulting query string.
     */
    public static String convertToQueryParameters(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (first) {
                    first = false;
                } else {
                    result.append("&");
                }
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}