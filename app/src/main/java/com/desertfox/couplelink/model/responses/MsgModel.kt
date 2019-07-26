package com.desertfox.couplelink.model.responses


import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class MsgModel(
    @SerializedName("bannedIndexRange")
    val bannedIndexRange: List<IndexRangeModel?>,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("writer")
    val writer: WriterModel
)