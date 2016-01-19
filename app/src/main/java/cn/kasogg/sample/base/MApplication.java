package cn.kasogg.sample.base;

import android.app.Application;

public class MApplication extends Application {
    private static MApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MApplication getInstance() {
        return instance;
    }
}
