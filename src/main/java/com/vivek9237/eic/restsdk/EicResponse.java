package com.vivek9237.eic.restsdk;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EicResponse {
    private final int responseCode;
    private final Map<String, String> headers;
    private final String body;

    public EicResponse(int responseCode, Map<String, String> headers, String body) {
        this.responseCode = responseCode;
        this.headers = headers;
        this.body = body;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public JsonObject getBodyAsJson() {
        JsonObject jsonObject;
        if(!EicClientUtils.isStringEmpty(body)){
            jsonObject = JsonParser.parseString(body).getAsJsonObject();
        } else{
            jsonObject = null;
        }
        return jsonObject;
    }
    @Override
    public String toString() {
		return "[responseCode=" + responseCode + ", headers=" + EicClientUtils.mapToString(headers) + ", body=" + body + "]";
	}
}