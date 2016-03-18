package com.kasogg.booster.util.http.data;

import java.util.Map;

/**
 * Author: KasoGG
 * Date:   2015-07-17 15:35
 */
public class NetError {
    public int statusCode;
    public String responseStr;
    public String errorMessage;
    public Map<String, String> headers;

    @Override
    public String toString() {
        return "statusCode=" + statusCode + ", errorMessage='" + errorMessage + ", responseStr='" + responseStr;
    }
}
