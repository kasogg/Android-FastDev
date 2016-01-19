# Android-Dev-Framework
集合了第三方类库的开发框架和常用工具类，方便快速开发。

## 工具版本
* Android Studio 1.5
* Gradle 2.8
* Android SDK 6.0(API 23)

包含以下类库：

* [OKHttp3](https://github.com/square/okhttp)
* [Glide](https://github.com/bumptech/glide)
* [FastJson](https://github.com/alibaba/fastjson)
* [EventBus](https://github.com/greenrobot/EventBus)
* 常用工具类

## HttpUtils

```
HttpUtils.post().url("http://www.baidu.com").params(prarms).files(files).headers(headers).tag(tag).build().execute(new StringResponseHandler() {
    @Override
    public void onBefore(RequestCall requestCall) {
        LogUtils.i(requestCall.getUrl());
        dialog = ProgressDialog.show(MainActivity.this, "Network", "loading");
    }

    @Override
    public void onSuccess(String responseStr, int statusCode, Map<String, String> headers) {
        LogUtils.i(responseStr);
    }

    @Override
    public void onFailure(NetError error) {

    }

    @Override
    public void onAfter() {
        dialog.dismiss();
    }
});
```
