package com.desertfox.couplelink.model.responses


import com.google.gson.annotations.SerializedName

data class MsgModel(
    @SerializedName("bannedIndexRange")
    val bannedIndexRange: IndexRangeModel?,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("writer")
    val writer: WriterModel
)