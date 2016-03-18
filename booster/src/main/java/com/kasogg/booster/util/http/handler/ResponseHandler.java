package com.kasogg.booster.util.http.handler;

import java.io.IOException;

import com.kasogg.booster.util.http.data.NetError;
import com.kasogg.booster.util.http.data.NetResponse;
import com.kasogg.booster.util.http.request.RequestCall;
import okhttp3.Response;

/**
 * Author: KasoGG
 * Date:   2015-07-17 15:34
 */
public interface ResponseHandler {
    void onSuccess(NetResponse response);

    void onFailure(NetError error);

    void onBefore(RequestCall requestCall);

    void onAfter();

    NetResponse parseResponse(Response response) throws IOException;
}