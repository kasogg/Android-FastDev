package com.kasogg.booster.util.http.request;

import java.util.Map;

import com.kasogg.booster.util.http.handler.ResponseHandler;

/**
 * Author: KasoGG
 * Date:   2016-01-18 13:46
 */
public interface RequestCall {

    String getUrl();

    Object getTag();

    Map<String, String> getParams();

    Map<String, String> getHeaders();

    void execute(ResponseHandler handler);

}