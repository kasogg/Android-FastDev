package cn.kasogg.booster.util.http.builder;

import cn.kasogg.booster.util.http.request.OKHttpRequestCall;
import cn.kasogg.booster.util.http.request.PostStringCall;
import okhttp3.MediaType;

/**
 * Author: KasoGG
 * Date:   2016-01-18 14:17
 */
public class PostStringBuilder extends OKHttpRequestCallBuilder<PostStringBuilder> {
    private String content;
    private MediaType mediaType;

    @Override
    public OKHttpRequestCall build() {
        return new PostStringCall(url, tag, params, headers, content, mediaType);
    }

    public PostStringBuilder content(String content) {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

}
