package com.thoughtworks.wechatmoment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.thoughtworks.wechatmoment.model.User
import com.thoughtworks.wechatmoment.utils.FileUtils
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        storageUserFile()
        initHeaderView()
    }

    private fun initHeaderView() {
        val user = getUser()
        Glide.with(this)
            .load(user.profileImage)
            .into(imageView_header)

        Glide.with(this)
            .load(user.avatar)
            .into(imageView_avatar);

        textView_username.text = user.nick
    }

    private fun getUser(): User {
        val userString = FileUtils().readerFile("userFile", this)
        return Gson().fromJson(userString, User::class.java)
    }

    private fun storageUserFile() {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(LogInterceptor())
            .build()

        // 2、创建Request对象
        val request = Request
            .Builder()
            .url("https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith")
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
                val fileName = "userFile"
                FileUtils().storageFile(fileName, jsonString, this@MainActivity)
            }
        })
    }
}
