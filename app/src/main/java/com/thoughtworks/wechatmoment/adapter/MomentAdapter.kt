package com.thoughtworks.wechatmoment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.thoughtworks.wechatmoment.R
import com.thoughtworks.wechatmoment.model.MomentItem
import com.thoughtworks.wechatmoment.model.User
import kotlinx.android.synthetic.main.moment_header_view.view.*
import kotlinx.android.synthetic.main.moment_item.view.*


class MomentAdapter(
    private val user: User,
    private val moments: List<MomentItem>,
    private val context: Context
) : RecyclerView.Adapter<ViewHolder?>() {

    private val mHeaderCount = 1
    private val contentItemCount = moments.size

    override fun getItemViewType(position: Int): Int {
        contentItemCount
        return if (mHeaderCount != 0 && position < mHeaderCount) {
            ITEM_TYPE_HEADER
        } else {
            ITEM_TYPE_CONTENT
        }
    }

    class ContentViewHolder(itemView: View) : ViewHolder(itemView)

    class HeaderViewHolder(itemView: View?) : ViewHolder(itemView!!)

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
            Glide.with(context)
                .load(user.profileImage)
                .into(holder.itemView.imageView_header)

            Glide.with(context)
                .load(user.avatar)
                .into(holder.itemView.imageView_avatar)
        } else {
            val currentItem = moments[position - mHeaderCount]

            holder.itemView.username_text_view.text = currentItem.sender.nick
            holder.itemView.content_text_view.text = currentItem.content
            Glide.with(context).load(currentItem.sender.avatar)
                .into(holder.itemView.moment_imageView_avatar)
        }
    }

    override fun getItemCount(): Int {
        return mHeaderCount + contentItemCount
    }

    companion object {
        const val ITEM_TYPE_HEADER = 0
        const val ITEM_TYPE_CONTENT = 1
    }

}