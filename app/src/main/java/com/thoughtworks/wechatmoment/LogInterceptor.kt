package com.thoughtworks.wechatmoment

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LogInterceptor: Interceptor {
    companion object{
        const val TAG = "LogInterceptor"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(TAG, "拦截器开始")
        // 获取请求对象
        val request = chain.request()
        val t1 = System.nanoTime()
        Log.d(TAG, "Sending request ${request.url} on ${chain.connection()} ${request.headers}")

        // 发起HTTP请求，并获取响应对象
        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        Log.d(TAG, String.format("Received response for %s in %.1fms%n%s",
            response.request.url, (t2 - t1) / 1e6, response.headers
        )
        )
        return response
    }

}
