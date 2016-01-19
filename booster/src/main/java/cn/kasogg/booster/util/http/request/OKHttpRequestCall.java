package cn.kasogg.booster.util.http.request;

import java.util.Map;

import cn.kasogg.booster.util.http.HttpUtils;
import cn.kasogg.booster.util.http.handler.ResponseHandler;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author: KasoGG
 * Date:   2016-01-18 13:58
 */
public abstract class OKHttpRequestCall implements RequestCall {
    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected OKHttpRequestCall(String url, Object tag, Map<String, String> params, Map<String, String> headers) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;

        if (url == null) {
            throw new IllegalArgumentException("Url can't be null.");
        }
    }

    @Override
    public void execute(ResponseHandler handler) {
        HttpUtils.getInstance().execute(this, handler);
    }

    public abstract Request buildRequest();

    protected abstract RequestBody buildRequestBody();

    @Override
    public String getUrl() {
        return this.url;
    }

    @Override
    public Object getTag() {
        return this.tag;
    }

    @Override
    public Map<String, String> getParams() {
        return this.params;
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }
}
