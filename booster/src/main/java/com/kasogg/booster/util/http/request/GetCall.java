package com.kasogg.booster.util.http.request;

import java.util.Map;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author: KasoGG
 * Date:   2016-01-18 13:50
 */
public class GetCall extends OKHttpRequestCall {

    public GetCall(String url, Object tag, Map<String, String> params, Map<String, String> headers) {
        super(url, tag, params, headers);
    }

    public Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(url).tag(tag).get();
        if (headers != null && headers.size() > 0) {
            builder.headers(Headers.of(headers));
        }
        return builder.build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return null;
    }

}
