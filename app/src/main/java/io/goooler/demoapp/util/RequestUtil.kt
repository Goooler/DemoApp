package io.goooler.demoapp.util

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

/**
 * OkHttp 请求简单封装
 */

object RequestUtil {
    private const val DEFAULT_URL = ""
    private const val CONTENT_TYPE_JSON = "application/json; charset=utf-8"

    /**
     * 普通 get 异步请求
     *
     * @param url      请求地址
     * @param listener 返回结果回调
     */
    fun get(url: String, listener: RequestListener) {
        val request = Request.Builder().url(DEFAULT_URL + url).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                ToastUtil.showToast(e.toString())
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                sendCallback(response, listener)
            }
        })
    }

    /**
     * 普通 post 异步请求
     *
     * @param url        请求地址
     * @param jsonString body 是 json 的方式
     * @param listener   返回结果回调
     */
    fun post(url: String, jsonString: String, listener: RequestListener) {
        val body = jsonString.toRequestBody(CONTENT_TYPE_JSON.toMediaTypeOrNull())
        val request = Request.Builder().url(DEFAULT_URL + url).post(body).build()
        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                ToastUtil.showToast(e.toString())
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                sendCallback(response, listener)
            }
        })
    }

    /**
     * 请求成功将返回的源 response 直接回调给发起方
     */
    private fun sendCallback(response: Response, listener: RequestListener) {
        if (response.isSuccessful) {
            var jsonString = ""
            try {
                jsonString = response.body.toString()
            } catch (e: IOException) {
                // do nothing
            }

            listener.response(response)
            listener.response(response, jsonString)
        }
    }

    /**
     * 请求结果回调给调用方的接口，可以让调用方实现匿名内部类时自由选择要覆写的方法
     * 覆写的方法决定回调的类型
     */
    abstract class RequestListener : RequestCallback {
        override fun response(rawResponse: Response) {

        }

        override fun response(rawResponse: Response, jsonString: String) {

        }
    }

    /**
     * RequestListener 要实现的一个接口，定义几种返回类型
     */
    interface RequestCallback {
        /**
         * 返回原始的 okhttp3.Response 不做任何处理
         *
         * @param rawResponse 源 Response
         */
        fun response(rawResponse: Response)

        /**
         * 自定义要回调给发起方的数据类型
         *
         * @param rawResponse 原始的 response
         * @param jsonString  body string
         */
        fun response(rawResponse: Response, jsonString: String)
    }
}
