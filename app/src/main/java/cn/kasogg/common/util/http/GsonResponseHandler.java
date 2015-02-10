package cn.kasogg.common.util.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import java.lang.reflect.ParameterizedType;

public abstract class GsonResponseHandler<T> extends BaseJsonHttpResponseHandler<T> {
    @Override
    protected T parseResponse(String rawJsonData, boolean isFailure) {
        if (isFailure) {
            return null;
        }
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(rawJsonData, clazz);
    }
}