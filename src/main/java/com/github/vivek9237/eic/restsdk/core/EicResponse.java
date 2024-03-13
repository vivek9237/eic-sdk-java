package com.github.vivek9237.eic.restsdk.core;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.github.vivek9237.eic.restsdk.utils.EicClientUtils;
import com.github.vivek9237.eic.restsdk.utils.EicJsonUtils;

/**
 * Represents a response from an EIC (Enterprise Identity Cloud) API request.
 */
public class EicResponse {
    private final int responseCode;
    private final Map<String, String> headers;
    private final String body;

    /**
     * Constructs an EicResponse object with the specified response code, headers,
     * and body.
     * 
     * @param responseCode The HTTP response code.
     * @param headers      The response headers.
     * @param body         The response body.
     */
    public EicResponse(int responseCode, Map<String, String> headers, String body) {
        this.responseCode = responseCode;
        this.headers = headers;
        this.body = body;
    }

    /**
     * Gets the HTTP response code.
     * 
     * @return The HTTP response code.
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Gets the response headers.
     * 
     * @return The response headers.
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Gets the response body.
     * 
     * @return The response body.
     */
    public String getBody() {
        return body;
    }

    /**
     * Parses the response body as a JsonObject.
     * 
     * @return The response body as a JsonObject.
     */
    public JsonObject getBodyAsJson() {
        JsonObject jsonObject;
        if (!EicClientUtils.isStringEmpty(body)) {
            jsonObject = EicJsonUtils.jsonStringToJsonObject(body);
        } else {
            jsonObject = null;
        }
        return jsonObject;
    }

    /**
     * Returns a string representation of the EicResponse object.
     * 
     * @return A string representation of the EicResponse object.
     */
    @Override
    public String toString() {
        // Convert the original map to a new map of type Map<String, Object>
        Map<String, Object> objectMap = new HashMap<>();
        headers.forEach((key, value) -> objectMap.put(key, value));
        return "[responseCode=" + responseCode + ", headers=" + EicJsonUtils.mapToJsonObjectString(objectMap) + ", body=" + body
                + "]";
    }
}