package cn.kasogg.sample.activity;

import android.app.Application;

import cn.kasogg.common.util.ImageUtils;

/**
 * Init CrashHandler
 */
public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
//        CrashHandler.getInstance().init(this);
        ImageUtils.init(this);
    }
}
