package cn.kasogg.sample.activity;

import android.app.Application;

import cn.kasogg.common.util.CrashHandler;

/**
 * Init CrashHandler
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
