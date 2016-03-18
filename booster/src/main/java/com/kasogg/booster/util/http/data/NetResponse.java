package com.kasogg.booster.util.http.data;

import java.util.Map;

/**
 * Author: KasoGG
 * Date:   2015-07-20 12:00
 */
public class NetResponse {
    public int statusCode;
    public String responseStr;
    public byte[] responseBody;
    public Map<String, String> headers;
}
