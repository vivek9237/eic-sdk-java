package com.github.vivek9237.eic.restsdk.core;

import java.util.HashMap;
import java.util.Map;
import com.github.vivek9237.eic.restsdk.utils.EicJsonUtils;

/**
 * Represents a request to be sent to an EIC (Enterprise Identity Cloud) API.
 */
public class EicRequest {
    private String url;
    private String method;
    private Map<String, String> headers;
    private String body;

    /**
     * Constructs an EicRequest object with the specified URL, HTTP method, headers,
     * and body.
     * 
     * @param url     The URL of the API endpoint.
     * @param method  The HTTP method (e.g., GET, POST).
     * @param headers The request headers.
     * @param body    The request body.
     */
    public EicRequest(String url, String method, Map<String, String> headers, String body) {
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }

    /**
     * Gets the URL of the API endpoint.
     * 
     * @return The URL of the API endpoint.
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Gets the HTTP method of the request.
     * 
     * @return The HTTP method of the request.
     */
    public String getMethod() {
        return this.method;
    }

    /**
     * Gets the request headers.
     * 
     * @return The request headers.
     */
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    /**
     * Gets the request body.
     * 
     * @return The request body.
     */
    public String getBody() {
        return this.body;
    }

    /**
     * Returns a string representation of the EicRequest object.
     * 
     * @return A string representation of the EicRequest object.
     */
    @Override
    public String toString() {
        // Convert the original map to a new map of type Map<String, Object>
        Map<String, Object> objectMap = new HashMap<>();
        headers.forEach((key, value) -> objectMap.put(key, value));
        return "[url=" + url + ", method=" + method + ", headers=" + EicJsonUtils.mapToJsonObjectString(objectMap) + ", body="
                + body + "]";
    }
}
