package cn.kasogg.sample.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.Map;

import cn.kasogg.booster.util.LogUtils;
import cn.kasogg.booster.util.http.HttpUtils;
import cn.kasogg.booster.util.http.data.NetError;
import cn.kasogg.booster.util.http.handler.StringResponseHandler;
import cn.kasogg.sample.R;

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