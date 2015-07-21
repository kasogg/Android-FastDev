package cn.kasogg.booster.util.http;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.squareup.okhttp.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import cn.kasogg.booster.util.http.data.NetError;
import cn.kasogg.booster.util.http.data.NetRequest;
import cn.kasogg.booster.util.http.data.NetResponse;
import cn.kasogg.booster.util.http.handler.StringResponseHandler;

public class HttpUtils {
    private static HttpUtils httpUtils;
    private static Context mContext;
    private static RequestQueue mRequestQueue;
    private static OkHttpClient mClient;

    private HttpUtils() {
    }

    public static synchronized HttpUtils getInstance(Context context) {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
            mContext = context;
        }
        return httpUtils;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext(), new OkHttpStack(getOkHttpClient()));
        }
        return mRequestQueue;
    }

    private OkHttpClient getOkHttpClient() {
        if (mClient == null) {
            mClient = new OkHttpClient();
        }
        return mClient;
    }

    private void sendRequest(int method, String url, Map<String, String> params, final StringResponseHandler handler) {
        Request request = new NetRequest(method, url, params, new Response.Listener<NetResponse>() {
            @Override
            public void onResponse(NetResponse response) {
                handler.onSuccess(response.responseStr, response.statusCode, response.headers);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetError netError = new NetError();
                netError.statusCode = error.networkResponse.statusCode;
                netError.errorMessage = error.getMessage();
                netError.headers = error.networkResponse.headers;
                try {
                    netError.responseStr = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                } catch (UnsupportedEncodingException e) {
                    netError.responseStr = new String(error.networkResponse.data);
                }
                handler.onFailure(netError);
            }
        });
        getRequestQueue().add(request);
    }

    public void get(String url, Map<String, String> params, final StringResponseHandler handler) {
        sendRequest(Request.Method.GET, url, params, handler);
    }

    public void get(String url, final StringResponseHandler handler) {
        get(url, null, handler);
    }

    public void post(String url, Map<String, String> params, final StringResponseHandler handler) {
        sendRequest(Request.Method.POST, url, params, handler);
    }
}