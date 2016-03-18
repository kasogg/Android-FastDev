package com.kasogg.booster.util.http.builder;

import java.util.Map;

import com.kasogg.booster.util.http.request.RequestCall;
import okhttp3.MediaType;

/**
 * Author: KasoGG
 * Date:   2016-01-18 11:57
 */
public interface RequestCallBuilder {
    public static final MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final String CHARSET_UTF8 = "UTF-8";

    RequestCallBuilder url(String url);

    RequestCallBuilder tag(Object tag);

    RequestCallBuilder params(Map<String, String> params);

    RequestCallBuilder addParams(String key, String value);

    RequestCallBuilder headers(Map<String, String> headers);

    RequestCallBuilder addHeader(String key, String value);

    RequestCall build();

}
