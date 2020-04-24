package com.thoughtworks.wechatmoment.utils

import android.content.Context
import com.thoughtworks.wechatmoment.LogInterceptor
import okhttp3.*
import java.io.IOException

class InternetUtils {
    fun storageFile(url: String, fileName: String, context: Context) {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LogInterceptor())
            .build()

        // 2、创建Request对象
        val request = Request
            .Builder()
            .url(url)
            .build()

        //3、通过okHttpClient的newCall方法获得一个Call对象
        val call = okHttpClient
            .newCall(request)

        // 4、请求
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()
                FileUtils().storageFile(fileName, jsonString, context)
            }
        })
    }
}