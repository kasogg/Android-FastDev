package cn.kasogg.booster.util.http.data;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 继承自Volley的Request类
 * Author: KasoGG
 * Date:   2015-07-20 12:00
 */
public class NetRequest extends Request<NetResponse> {
    private final Listener<NetResponse> mListener;
    Map<String, String> mParams;

    public NetRequest(int method, String url, Map<String, String> params, Listener<NetResponse> listener, ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return mParams;
    }

    @Override
    protected Response<NetResponse> parseNetworkResponse(NetworkResponse response) {
        NetResponse netResponse = new NetResponse();
        netResponse.statusCode = response.statusCode;
        netResponse.headers = response.headers;
        netResponse.responseBytes = response.data;
        try {
            netResponse.responseStr = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            netResponse.responseStr = new String(response.data);
        }
        return Response.success(netResponse, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetResponse response) {
        mListener.onResponse(response);
    }

}
