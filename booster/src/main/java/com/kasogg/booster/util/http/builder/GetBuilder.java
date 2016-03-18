package com.kasogg.booster.util.http.builder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import com.kasogg.booster.util.http.request.GetCall;
import com.kasogg.booster.util.http.request.OKHttpRequestCall;

/**
 * Author: KasoGG
 * Date:   2016-01-18 14:17
 */
public class GetBuilder extends OKHttpRequestCallBuilder<GetBuilder> {

    @Override
    public OKHttpRequestCall build() {
        if (params != null && params.size() > 0) {
            url = url + "?" + encodeParameters(params);
        }
        return new GetCall(url, tag, params, headers);
    }

    private String encodeParameters(Map<String, String> params) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), CHARSET_UTF8));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), CHARSET_UTF8));
                encodedParams.append('&');
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported: " + CHARSET_UTF8, e);
        }
        String result = encodedParams.toString();
        if (result.endsWith("&")) {
            result = result.substring(0, result.lastIndexOf("&"));
        }
        return result;
    }
}
