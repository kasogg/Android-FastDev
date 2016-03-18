package com.kasogg.booster.util.http.request;

import com.kasogg.booster.util.http.data.FileInput;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author: KasoGG
 * Date:   2016-01-18 13:50
 */
public class PostFormCall extends OKHttpRequestCall {
    private List<FileInput> files;

    public PostFormCall(String url, Object tag, Map<String, String> params, Map<String, String> headers, List<FileInput> files) {
        super(url, tag, params, headers);
        this.files = files;
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
        if (files == null || files.isEmpty()) {
            if (params != null && params.size() > 0) {
                return buildFormBody();
            } else {
                return buildEmptyBody();
            }
        } else {
            return buildMultipartBody();
        }
    }

    private RequestBody buildFormBody() {
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            formEncodingBuilder.add(entry.getKey(), entry.getValue());
        }
        return formEncodingBuilder.build();
    }

    private RequestBody buildEmptyBody() {
        return RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), new byte[]{});
    }

    private RequestBody buildMultipartBody() {
        MultipartBody.Builder multipartBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        for (FileInput fileInput : files) {
            RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.fileName)), fileInput.file);
            multipartBuilder.addFormDataPart(fileInput.key, fileInput.fileName, fileBody);
        }
        return multipartBuilder.build();
    }

    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
