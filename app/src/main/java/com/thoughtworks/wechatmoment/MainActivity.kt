package com.thoughtworks.wechatmoment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.thoughtworks.wechatmoment.adapter.MomentAdapter
import com.thoughtworks.wechatmoment.model.MomentItem
import com.thoughtworks.wechatmoment.model.Moments
import com.thoughtworks.wechatmoment.model.User
import com.thoughtworks.wechatmoment.utils.FileUtils
import com.thoughtworks.wechatmoment.utils.InternetUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InternetUtils().storageFile("https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith",
            "userFile", this)
        InternetUtils().storageFile("https://thoughtworks-mobile-2018.herokuapp.com/user/jsmith/tweets",
            "momentsFile", this)

        moment_recycler_view.adapter = MomentAdapter(getUser(), getMoments(), this)
        moment_recycler_view.layoutManager = LinearLayoutManager(this)
        moment_recycler_view.setHasFixedSize(true)
    }

    private fun getUser(): User {
        val userString = FileUtils().readerFile("userFile", this)
        return Gson().fromJson(userString, User::class.java)
    }

    private fun getMoments(): List<MomentItem> {
        val momentsString = FileUtils().readerFile("momentsFile", this)
        val moments = Gson().fromJson(momentsString, Moments::class.java)
        return moments.filter {
            it.content != null || it.images != null
        }
    }
}
