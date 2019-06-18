package com.desertfox.couplelink.model.request


import com.google.gson.annotations.SerializedName

data class MsgRequest(
    @SerializedName("message")
    val message: String
)