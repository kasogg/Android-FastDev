package com.kasogg.booster.util.http.handler;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import com.kasogg.booster.util.http.data.NetResponse;
import com.kasogg.booster.util.http.request.RequestCall;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static okhttp3.internal.Util.UTF_8;

/**
 * Author: KasoGG
 * Date:   2015-07-17 15:34
 */
public abstract class OKHttpResponseHandler implements ResponseHandler {
    @Override
    public NetResponse parseResponse(Response response) throws IOException {
        NetResponse netResponse = new NetResponse();
        Map<String, String> responseHeaders = new HashMap<>();
        if (response.headers() != null && response.headers().size() > 0) {
            for (String key : response.headers().names()) {
                responseHeaders.put(key, response.headers().get(key));
            }
        }
        netResponse.statusCode = response.code();
        netResponse.headers = responseHeaders;
        netResponse.responseBody = response.body().bytes();
        netResponse.responseStr = new String(netResponse.responseBody, getCharset(response.body()));
        return netResponse;
    }

    private Charset getCharset(ResponseBody body) {
        MediaType contentType = body.contentType();
        return contentType != null ? contentType.charset(UTF_8) : UTF_8;
    }

    @Override
    public void onBefore(RequestCall requestCall) {

    }

    @Override
    public void onAfter() {

    }
}