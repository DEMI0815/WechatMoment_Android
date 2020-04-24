package com.thoughtworks.wechatmoment.model

data class MomentItem(
    val comments: List<Comment>?,
    val content: String?,
    val images: List<Image>?,
    val sender: Sender
)

data class Comment(
    val content: String,
    val sender: Sender
)

data class Image(
    val url: String
)