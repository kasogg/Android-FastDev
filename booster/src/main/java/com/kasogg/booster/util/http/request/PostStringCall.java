package com.kasogg.booster.util.http.request;

import java.util.Map;

import com.kasogg.booster.util.http.builder.RequestCallBuilder;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author: KasoGG
 * Date:   2016-01-18 13:50
 */
public class PostStringCall extends OKHttpRequestCall {

    private String content;
    private MediaType mediaType;

    public PostStringCall(String url, Object tag, Map<String, String> params, Map<String, String> headers, String content, MediaType mediaType) {
        super(url, tag, params, headers);
        this.content = content;
        this.mediaType = mediaType;
        if (this.content == null) {
            throw new IllegalArgumentException("The content can not be null!");
        }
        if (this.mediaType == null) {
            this.mediaType = RequestCallBuilder.MEDIA_TYPE_PLAIN;
        }
    }

    public Request buildRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(url).tag(tag).post(buildRequestBody());
        if (headers != null && headers.size() > 0) {
            builder.headers(Headers.of(headers));
        }
        return builder.build();
    }

    @Override
    protected RequestBody buildRequestBody() {
        return RequestBody.create(mediaType, content);
    }

}
