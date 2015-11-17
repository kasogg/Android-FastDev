package cn.kasogg.sample.base;

import android.app.Application;

import cn.kasogg.booster.util.http.HttpUtils;

public class MApplication extends Application {
    private static MApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        HttpUtils.init(this);
    }

    public static MApplication getInstance() {
        return instance;
    }
}
