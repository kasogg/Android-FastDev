package com.kasogg.sample.base;

import android.app.Activity;
import android.os.Bundle;

import com.kasogg.booster.util.ActivityManager;

public class BaseActivity extends Activity {
    protected MApplication mApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MApplication) getApplication();
        ActivityManager.getInstance().pushActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);
    }

}