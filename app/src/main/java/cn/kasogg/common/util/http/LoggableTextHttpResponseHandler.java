package cn.kasogg.common.util.http;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import cn.kasogg.common.util.LogUtils;

/**
 * 添加了Log输出信息的网络处理类
 */
public abstract class LoggableTextHttpResponseHandler extends TextHttpResponseHandler {

    public void onSuccessBefore(int statusCode, Header[] headers, String responseString) {
        LogUtils.i("onSuccess-" + statusCode, responseString);
        onSuccess(statusCode, headers, responseString);
    }

    public void onFailureBefore(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        LogUtils.i("onFailure-" + statusCode, responseString);
        if (LogUtils.IS_LOG) {
            throwable.printStackTrace();
        }
        onFailure(statusCode, headers, responseString, throwable);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBytes) {
        onSuccessBefore(statusCode, headers, getResponseString(responseBytes, getCharset()));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBytes, Throwable throwable) {
        onFailureBefore(statusCode, headers, getResponseString(responseBytes, getCharset()), throwable);
    }
}
