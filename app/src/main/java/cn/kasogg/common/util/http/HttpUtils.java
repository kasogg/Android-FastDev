package cn.kasogg.common.util.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public class HttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    private HttpUtils() {
        throw new AssertionError();
    }

    public static AsyncHttpClient getAsyncHttpClient() {
        return client;
    }

    /**
     * 以GET方式发送请求，返回实体对象
     *
     * @param url             请求的Url
     * @param params          请求的参数
     * @param responseHandler GsonResponseHandler,返回实体model
     */
    public static void get(String url, RequestParams params, GsonResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    /**
     * 以POST方式发送请求，返回实体对象
     *
     * @param url             请求的Url
     * @param params          请求的参数
     * @param responseHandler GsonResponseHandler,返回实体model
     */
    public static void post(String url, RequestParams params, GsonResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    /**
     * 以GET方式发送请求，返回String
     *
     * @param url             请求的Url
     * @param params          请求的参数
     * @param responseHandler TextHttpResponseHandler,返回String
     */
    public static void get(String url, RequestParams params, LoggableTextHttpResponseHandler responseHandler) {
        client.get(url, params, responseHandler);
    }

    /**
     * 以POST方式发送请求，返回String
     *
     * @param url             请求的Url
     * @param params          请求的参数
     * @param responseHandler TextHttpResponseHandler,返回String
     */
    public static void post(String url, RequestParams params, LoggableTextHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    /**
     * 以POST方式上传单个文件
     *
     * @param url             请求的Url
     * @param key             请求的key
     * @param file            请求的File
     * @param responseHandler TextHttpResponseHandler,返回String
     */
    public static void uploadFile(String url, String key, File file, LoggableTextHttpResponseHandler responseHandler) throws FileNotFoundException {
        RequestParams params = new RequestParams();
        params.put(key, file);
        client.post(url, params, responseHandler);
    }

    /**
     * 以POST方式上传多个文件
     *
     * @param url             请求的Url
     * @param files           请求的key以及file集合
     * @param responseHandler TextHttpResponseHandler,返回String
     */
    public static void uploadFiles(String url, Map<String, File> files, LoggableTextHttpResponseHandler responseHandler) throws FileNotFoundException {
        RequestParams params = new RequestParams();
        for (String key : files.keySet()) {
            params.put(key, files.get(key));
        }
        client.post(url, params, responseHandler);
    }
}