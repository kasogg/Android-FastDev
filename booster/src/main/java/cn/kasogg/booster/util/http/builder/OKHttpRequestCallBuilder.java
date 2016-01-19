package cn.kasogg.booster.util.http.builder;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.kasogg.booster.util.http.request.OKHttpRequestCall;

/**
 * Author: KasoGG
 * Date:   2016-01-18 13:34
 */
public abstract class OKHttpRequestCallBuilder<T extends OKHttpRequestCallBuilder> implements RequestCallBuilder {
    protected String url;
    public Object tag;
    protected Map<String, String> headers;
    protected Map<String, String> params;

    @Override
    public T url(String url) {
        this.url = url;
        return (T) this;
    }

    @Override
    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    @Override
    public T params(Map params) {
        this.params = params;
        return (T) this;
    }

    @Override
    public T addParams(String key, String value) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, value);
        return (T) this;
    }

    @Override
    public T headers(Map headers) {
        this.headers = headers;
        return (T) this;
    }

    @Override
    public T addHeader(String key, String value) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, value);
        return (T) this;
    }

    @Override
    public abstract OKHttpRequestCall build();
}
