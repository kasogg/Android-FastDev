package com.kasogg.booster.util.http;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.kasogg.booster.util.http.builder.GetBuilder;
import com.kasogg.booster.util.http.builder.PostFormBuilder;
import com.kasogg.booster.util.http.builder.PostStringBuilder;
import com.kasogg.booster.util.http.data.NetError;
import com.kasogg.booster.util.http.data.NetResponse;
import com.kasogg.booster.util.http.handler.OKHttpResponseHandler;
import com.kasogg.booster.util.http.handler.ResponseHandler;
import com.kasogg.booster.util.http.request.OKHttpRequestCall;
import com.kasogg.booster.util.http.request.RequestCall;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author: KasoGG
 * Date:   2016-01-18 16:50
 */
public class HttpUtils {
    private OkHttpClient mClient;
    private Handler mHandler;
    private static HttpUtils mInstance;

    private HttpUtils() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static HttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (HttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }

    public void execute(RequestCall requestCall, ResponseHandler handler) {
        if (handler != null) {
            handler.onBefore(requestCall);
        }
        OKHttpRequestCall call = (OKHttpRequestCall) requestCall;
        Request request = call.buildRequest();
        deliverRequest(request, (OKHttpResponseHandler) handler);
    }

    protected void deliverRequest(Request request, final OKHttpResponseHandler handler) {
        getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                NetResponse netResponse = handler.parseResponse(response);
                if (response.isSuccessful()) {
                    sendSuccessCallback(handler, netResponse);
                } else {
                    NetError netError = new NetError();
                    netError.errorMessage = response.message();
                    netError.statusCode = netResponse.statusCode;
                    netError.headers = netResponse.headers;
                    netError.responseStr = netResponse.responseStr;
                    sendFailureCallback(handler, netError);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Request request = call.request();
                Map<String, String> headers = new HashMap<>();
                for (String key : request.headers().names()) {
                    headers.put(key, request.headers().get(key));
                }

                NetError netError = new NetError();
                netError.errorMessage = e.toString();
                netError.statusCode = 0;
                netError.headers = headers;
                netError.responseStr = "";

                sendFailureCallback(handler, netError);
            }
        });
    }

    private void sendSuccessCallback(final ResponseHandler handler, final NetResponse response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                handler.onSuccess(response);
                handler.onAfter();
            }
        });
    }

    private void sendFailureCallback(final ResponseHandler handler, final NetError netError) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                handler.onFailure(netError);
                handler.onAfter();
            }
        });
    }

    public OkHttpClient getOkHttpClient() {
        if (mClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            mClient = builder.build();
        }
        return mClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

}
