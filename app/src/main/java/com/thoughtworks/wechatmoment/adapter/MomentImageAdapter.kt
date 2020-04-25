package com.thoughtworks.wechatmoment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thoughtworks.wechatmoment.R
import com.thoughtworks.wechatmoment.model.Image
import kotlinx.android.synthetic.main.moment_image_item.view.*

class MomentImageAdapter(
    private val images: List<Image>,
    private val fragmentActivity: FragmentActivity
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class MomentImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MomentImageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.moment_image_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(fragmentActivity).load(images[position].url).placeholder(R.drawable.ic_texture)
            .into(holder.itemView.moment_image_view)
    }
}