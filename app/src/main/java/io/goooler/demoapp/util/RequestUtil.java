package io.goooler.demoapp.util;

import androidx.annotation.NonNull;

import java.io.IOException;

import io.goooler.demoapp.R;
import io.goooler.demoapp.base.BaseApplication;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHttp 请求简单封装
 */

public class RequestUtil {
    private static final String DEFAULT_URL = "";
    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";

    /**
     * 普通 get 异步请求
     *
     * @param url      请求地址
     * @param listener 返回结果回调
     */
    public static void getRequest(String url, BaseRequestListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(DEFAULT_URL + url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showFailToast();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendCallback(response, listener);
            }
        });
    }

    /**
     * 普通 post 异步请求
     *
     * @param url        请求地址
     * @param jsonString body 是 json 的方式
     * @param listener   返回结果回调
     */
    public static void postRequest(String url, String jsonString, BaseRequestListener listener) {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(CONTENT_TYPE_JSON), jsonString);
        Request request = new Request.Builder().url(DEFAULT_URL + url).post(body).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showFailToast();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendCallback(response, listener);
            }
        });
    }

    /**
     * 请求成功将返回的源 response 直接回调给发起方
     */
    private static void sendCallback(@NonNull Response response, BaseRequestListener listener) {
        if (response.isSuccessful()) {
            String jsonString = null;
            try {
                jsonString = response.body().string();
            } catch (IOException e) {
                // do nothing
            }
            listener.response(response);
            listener.response(response, jsonString);
        }
    }

    /**
     * 请求失败一律弹出 “请求失败” 提示，需要先切换到主线程
     */
    private static void showFailToast() {
        BaseApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showToast(R.string.request_failed);
            }
        });
    }

    /**
     * 请求结果回调给调用方的接口，可以让调用方实现匿名内部类时自由选择要覆写的方法
     * 覆写的方法决定回调的类型
     */
    public static abstract class BaseRequestListener implements RequestCallback {
        @Override
        public void response(Response rawResponse) {

        }

        @Override
        public void response(Response rawResponse, String jsonString) {

        }
    }

    /**
     * BaseRequestListener 要实现的一个接口，定义几种返回类型
     */
    public interface RequestCallback {
        /**
         * 返回原始的 okhttp3.Response 不做任何处理
         *
         * @param rawResponse 源 Response
         */
        void response(Response rawResponse);

        /**
         * 自定义要回调给发起方的数据类型
         *
         * @param rawResponse 原始的 response
         * @param jsonString  body string
         */
        void response(Response rawResponse, String jsonString);
    }
}
