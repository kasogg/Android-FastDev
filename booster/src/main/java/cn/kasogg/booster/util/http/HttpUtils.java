package cn.kasogg.booster.util.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.kasogg.booster.util.LogUtils;
import cn.kasogg.booster.util.http.data.NetError;
import cn.kasogg.booster.util.http.handler.StringResponseHandler;

public class HttpUtils {
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private static Context mContext;
    private static OkHttpClient mClient;
    private static Handler mHandler;

    private HttpUtils() {
    }

    public static void init(Context context) {
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    private static void sendRequest(String method, String url, Map<String, String> params, final Map<String, String> headers, Object tag, final StringResponseHandler handler) {
        Request.Builder builder = new Request.Builder();
        // Set method & params
        if ("GET".equals(method)) {
            builder.get();
            if (params != null && params.size() > 0) {
                url = url + "?" + encodeParameters(params);
            }
        } else {
            if (params != null && params.size() > 0) {
                FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    formEncodingBuilder.add(entry.getKey(), entry.getValue());
                }
                builder.method(method, formEncodingBuilder.build());
            } else {
                builder.method(method, RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), new byte[]{})); // Empty body
            }
        }
        builder.url(url).tag(tag);
        if (headers != null && headers.size() > 0) {
            builder.headers(Headers.of(headers));
        }

        Request request = builder.build();
        deliverRequest(request, handler);
    }

    private static void deliverRequest(Request request, final StringResponseHandler handler) {
        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                final String responseStr = response.body().string();
                // Parse headers
                Map<String, String> responseHeaders = new HashMap<>();
                if (response.headers() != null && response.headers().size() > 0) {
                    for (String key : response.headers().names()) {
                        responseHeaders.put(key, response.headers().get(key));
                    }
                }
                if (response.isSuccessful()) {
                    LogUtils.i(responseStr);
                    sendSuccessCallback(handler, responseStr, response.code(), responseHeaders);
                } else {
                    NetError netError = new NetError();
                    netError.errorMessage = response.message();
                    netError.statusCode = response.code();
                    netError.headers = responseHeaders;
                    netError.responseStr = responseStr;
                    LogUtils.i(netError);
                    sendFailureCallback(handler, netError);
                }
            }

            @Override
            public void onFailure(Request request, IOException e) {
                // Parse headers
                Map<String, String> headers = new HashMap<>();
                for (String key : request.headers().names()) {
                    headers.put(key, request.headers().get(key));
                }

                NetError netError = new NetError();
                netError.errorMessage = e.toString();
                netError.statusCode = 0;
                netError.headers = headers;
                netError.responseStr = "";

                LogUtils.i(netError);
                sendFailureCallback(handler, netError);
            }

        });
    }

    private static void sendSuccessCallback(final StringResponseHandler responseHandler, final String responseStr, final int statusCode, final Map<String, String> headers) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                responseHandler.onSuccess(responseStr, statusCode, headers);
            }
        });
    }

    private static void sendFailureCallback(final StringResponseHandler handler, final NetError netError) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                handler.onFailure(netError);
            }
        });
    }

    public static void get(String url, final StringResponseHandler handler) {
        sendRequest(METHOD_GET, url, null, null, null, handler);
    }

    public static void get(String url, Map<String, String> params, final StringResponseHandler handler) {
        sendRequest(METHOD_GET, url, params, null, null, handler);
    }

    public static void get(String url, Map<String, String> params, Map<String, String> headers, Object tag, final StringResponseHandler handler) {
        sendRequest(METHOD_GET, url, params, headers, tag, handler);
    }

    public static void post(String url, final StringResponseHandler handler) {
        sendRequest(METHOD_POST, url, null, null, null, handler);
    }

    public static void post(String url, Map<String, String> params, final StringResponseHandler handler) {
        sendRequest(METHOD_POST, url, params, null, null, handler);
    }

    public static void post(String url, Map<String, String> params, Map<String, String> headers, Object tag, final StringResponseHandler handler) {
        sendRequest(METHOD_POST, url, params, headers, tag, handler);
    }

    public static void upload(String url, Map<String, String> params, Map<String, String> uploadFiles, final StringResponseHandler handler, Object tag, Map<String, String> headers) {
        MultipartBuilder multipartBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);
        if (params != null && params.size() > 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                multipartBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
        }
        if (uploadFiles != null && uploadFiles.size() > 0) {
            for (Map.Entry<String, String> entry : uploadFiles.entrySet()) {
                String key = entry.getKey();
                File file = new File(entry.getValue());
                String fileName = file.getName();
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                multipartBuilder.addFormDataPart(key, fileName, fileBody);
            }
        }

        Request.Builder builder = new Request.Builder();
        builder.url(url).tag(tag).post(multipartBuilder.build());
        if (headers != null && headers.size() > 0) {
            builder.headers(Headers.of(headers));
        }
        deliverRequest(builder.build(), handler);
    }

    public static OkHttpClient getOkHttpClient() {
        if (mClient == null) {
            mClient = new OkHttpClient();
            mClient.setConnectTimeout(10, TimeUnit.SECONDS);
            mClient.setReadTimeout(10, TimeUnit.SECONDS);
            mClient.setWriteTimeout(30, TimeUnit.SECONDS);
        }
        return mClient;
    }

    public static String encodeParameters(Map<String, String> params) {
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

    private static String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

}