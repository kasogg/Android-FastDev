package com.kasogg.sample.activity;

import android.app.Activity;
import android.os.Bundle;

import com.kasogg.booster.util.LogUtils;
import com.kasogg.booster.util.http.HttpUtils;
import com.kasogg.booster.util.http.data.NetError;
import com.kasogg.booster.util.http.handler.StringResponseHandler;
import com.kasogg.sample.R;

import java.util.Map;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtils.get().url("https://www.baidu.com").build().execute(new StringResponseHandler() {
            @Override
            public void onSuccess(String responseStr, int statusCode, Map<String, String> headers) {
                LogUtils.i(responseStr);
            }

            @Override
            public void onFailure(NetError error) {
            }
        });
    }

}