package com.thoughtworks.wechatmoment.model

import com.google.gson.annotations.SerializedName

data class User(
    val avatar: String,
    val nick: String,
    @SerializedName("profile-image")
    val profileImage: String,
    val username: String
)