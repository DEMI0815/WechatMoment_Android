package com.thoughtworks.wechatmoment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.thoughtworks.wechatmoment.Constant.MOMENTS_URL
import com.thoughtworks.wechatmoment.Constant.USER_URL
import com.thoughtworks.wechatmoment.adapter.MomentAdapter
import com.thoughtworks.wechatmoment.model.MomentItem
import com.thoughtworks.wechatmoment.model.Moments
import com.thoughtworks.wechatmoment.model.User
import com.thoughtworks.wechatmoment.utils.FileUtils
import com.thoughtworks.wechatmoment.utils.InternetUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var displayCount = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        InternetUtils().storageFile(USER_URL, "userFile", this)
        InternetUtils().storageFile(MOMENTS_URL, "momentsFile", this)

        val mAdapter = MomentAdapter(getUser(), this)
        initView(mAdapter)

        refresh_layout.setOnRefreshListener {
            it.finishRefresh(1000)
            displayCount = 5
            mAdapter.setList(getMoments().subList(0, displayCount))
            mAdapter.notifyDataSetChanged()
        }

        refresh_layout.setOnLoadMoreListener {
            it.finishLoadMore(1000)
            displayCount += 5
            if (displayCount > getMoments().size) {
                mAdapter.setList(getMoments())
                mAdapter.notifyDataSetChanged()
                it.finishLoadMoreWithNoMoreData()
            } else {
                mAdapter.setList(getMoments().subList(0, displayCount))
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initView(mAdapter: MomentAdapter) {
        moment_recycler_view.adapter = mAdapter
        moment_recycler_view.layoutManager = LinearLayoutManager(this)
        moment_recycler_view.setHasFixedSize(true)
        mAdapter.setList(getMoments().subList(0, 5))
        mAdapter.notifyDataSetChanged()
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
