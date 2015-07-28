package cn.kasogg.sample.activity;

import android.app.Activity;
import android.os.Bundle;

import java.util.Map;

import cn.kasogg.booster.util.http.HttpUtils;
import cn.kasogg.booster.util.http.data.NetError;
import cn.kasogg.booster.util.http.handler.StringResponseHandler;
import cn.kasogg.sample.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HttpUtils.getInstance(this).get("http://www.baidu.com", new StringResponseHandler() {
            @Override
            public void onSuccess(String response, int statusCode, Map<String, String> headers) {
            }

            @Override
            public void onFailure(NetError error) {
            }
        });
    }

}