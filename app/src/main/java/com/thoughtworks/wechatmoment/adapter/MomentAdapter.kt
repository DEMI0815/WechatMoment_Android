package com.thoughtworks.wechatmoment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.thoughtworks.wechatmoment.R
import com.thoughtworks.wechatmoment.model.MomentItem
import com.thoughtworks.wechatmoment.model.User
import com.thoughtworks.wechatmoment.view.MultiImageView
import kotlinx.android.synthetic.main.moment_header_view.view.*
import kotlinx.android.synthetic.main.moment_item.view.*


class MomentAdapter(
    private val user: User,
    private val fragmentActivity: FragmentActivity
) : RecyclerView.Adapter<ViewHolder?>() {

    private var moments: List<MomentItem> = ArrayList()

    fun setList(moments: List<MomentItem>) {
        this.moments = moments
    }

    private val mHeaderCount = 1

    override fun getItemViewType(position: Int): Int {
        return if (mHeaderCount != 0 && position < mHeaderCount) {
            ITEM_TYPE_HEADER
        } else {
            ITEM_TYPE_CONTENT
        }
    }

    class ContentViewHolder(itemView: View) : ViewHolder(itemView)

    class HeaderViewHolder(itemView: View) : ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == ITEM_TYPE_HEADER) {
            return HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.moment_header_view,
                    parent,
                    false
                )
            )
        } else {
            return ContentViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.moment_item,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.itemView.textView_username.text = user.nick
            Glide.with(fragmentActivity)
                .load(user.profileImage)
                .placeholder(R.drawable.ic_texture)
                .into(holder.itemView.imageView_header)

            Glide.with(fragmentActivity)
                .load(user.avatar)
                .placeholder(R.drawable.ic_texture)
                .into(holder.itemView.imageView_avatar)
        } else {
            val currentItem = moments[position - mHeaderCount]

            holder.itemView.username_text_view.text = currentItem.sender.nick
            holder.itemView.content_text_view.text = currentItem.content
            Glide.with(fragmentActivity).load(currentItem.sender.avatar)
                .placeholder(R.drawable.ic_texture)
                .into(holder.itemView.moment_imageView_avatar)

            if (currentItem.images != null) {
                val urlList: MutableList<String> = mutableListOf()
                currentItem.images.forEach {
                    urlList.add(it.url)
                }
                holder.itemView.multi_image.setList(urlList)
                holder.itemView.multi_image.setOnItemClickListener(object :
                    MultiImageView.OnItemClickListener {
                    override fun onItemClick(view: View?, position: Int) {
                        val num = position + 1
                        Toast.makeText(fragmentActivity, "点击第$num" + "个", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            if (currentItem.comments != null) {
                holder.itemView.comments_recyclerView.adapter = CommentAdapter(currentItem.comments)
                holder.itemView.comments_recyclerView.layoutManager =
                    LinearLayoutManager(fragmentActivity)
                holder.itemView.comments_recyclerView.setHasFixedSize(true)
            }
        }
    }

    override fun getItemCount(): Int {
        return mHeaderCount + moments.size
    }

    companion object {
        const val ITEM_TYPE_HEADER = 0
        const val ITEM_TYPE_CONTENT = 1
    }

}