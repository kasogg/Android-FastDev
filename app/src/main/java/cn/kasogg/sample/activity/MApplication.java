package cn.kasogg.sample.activity;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Init CrashHandler
 */
public class MApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
