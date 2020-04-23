package com.thoughtworks.wechatmoment

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.thoughtworks.wechatmoment.model.User
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LogInterceptor())
            .build()

        // 2、创建Request对象
        val request = Request
            .Builder()
            .url("http://thoughtworks-ios.herokuapp.com/user/jsmith")
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

                //存json数据到file
                //TODO

                val jsonString = response.body?.string()
                val fileName = "userFile"
                openFileOutput(fileName, Context.MODE_PRIVATE).use {
                    if (jsonString != null) {
                        it.write(jsonString.toByteArray())
                    }
                }

                val result = Gson().fromJson(jsonString, User::class.java)
                println(result)
            }
        })
    }
}
