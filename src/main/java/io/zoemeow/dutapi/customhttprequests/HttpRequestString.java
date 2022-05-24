package io.zoemeow.dutapi.customhttprequests;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class HttpRequestString {
    ArrayList<HttpRequestParameters> httpRequestParameters = null;

    public HttpRequestString() {
        this.httpRequestParameters = new ArrayList<HttpRequestParameters>();
    }

    public HttpRequestString(ArrayList<HttpRequestParameters> array) {
        this.httpRequestParameters = array;
    }

    public void addRequest(HttpRequestParameters param) {
        this.httpRequestParameters.add(param);
    }

    public HttpRequestParameters getRequests(int position) {
        return this.httpRequestParameters.get(position);
    }

    public void removeRequests(int position) {
        this.httpRequestParameters.remove(position);
    }

    public void removeRequests(Object obj) {
        this.httpRequestParameters.remove(obj);
    }

    public String toURLEncode() throws NullPointerException, UnsupportedEncodingException {
        String request = "";

        Boolean first = true;

        if (httpRequestParameters == null)
            throw new NullPointerException("HttpRequestParameters is null!");

        for (HttpRequestParameters item : httpRequestParameters) {
            if (!first)
                request += "&";
            else first = false;

            request += URLEncoder.encode(item.getName(), "UTF-8")
                    + "="
                    + URLEncoder.encode(item.getValue(), "UTF-8");
        }

        return request;
    }

    public byte[] toURLEncodeByteArray(String charsetName) throws UnsupportedEncodingException {
        return this.toURLEncode().getBytes(charsetName);
    }
}
