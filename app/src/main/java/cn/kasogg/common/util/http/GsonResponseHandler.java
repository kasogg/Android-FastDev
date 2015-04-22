package cn.kasogg.common.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

public abstract class GsonResponseHandler<T> extends BaseJsonHttpResponseHandler<T> {
    @Override
    protected T parseResponse(String rawJsonData, boolean isFailure) {
        if (isFailure) {
            return null;
        }
        return JSON.parseObject(rawJsonData, new TypeReference<T>() {
        }.getType());
    }
}