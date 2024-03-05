package com.vivek9237.eic.restsdk;

import java.util.Map;

public class EicRequest {
    private String url;
    private String method;
    private Map<String,String> headers;
    private String body;
    public EicRequest(String url, String method, Map<String,String> headers, String body ){
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = body;
    }
    public String getUrl(){
        return this.url;
    }
    public String getMethod(){
        return this.method;
    }
    public Map<String,String> getHeaders(){
        return this.headers;
    }
    public String getBody(){
        return this.body;
    }
    @Override
    public String toString(){
        return "[url=" + url + ", method=" + method +  ", headers=" + EicClientUtils.mapToString(headers) + ", body=" + body + "]";
    }
}
