package cn.kasogg.booster.util.http.handler;

import java.util.Map;

import cn.kasogg.booster.util.http.data.NetResponse;

/**
 * Author: KasoGG
 * Date:   2015-07-17 15:34
 */
public abstract class StringResponseHandler extends OKHttpResponseHandler {
    public void onSuccess(NetResponse response) {
        onSuccess(response.responseStr, response.statusCode, response.headers);
    }

    public abstract void onSuccess(String responseStr, int statusCode, Map<String, String> headers);

}