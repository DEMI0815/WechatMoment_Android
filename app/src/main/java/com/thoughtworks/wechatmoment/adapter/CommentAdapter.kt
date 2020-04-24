package com.thoughtworks.wechatmoment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thoughtworks.wechatmoment.R
import com.thoughtworks.wechatmoment.model.Comment
import kotlinx.android.synthetic.main.moment_comment_item.view.*

class CommentAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MomentImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MomentImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.moment_comment_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.comment_name_textView.text = comments[position].sender.nick
        holder.itemView.comment_content_textView.text = comments[position].content
    }
}