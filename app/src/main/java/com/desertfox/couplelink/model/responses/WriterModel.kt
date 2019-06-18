package com.desertfox.couplelink.model.responses


import com.google.gson.annotations.SerializedName

data class WriterModel(
    @SerializedName("connectionNumber")
    val connectionNumber: String,
    @SerializedName("coupleId")
    val coupleId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String,
    @SerializedName("status")
    val status: String
)