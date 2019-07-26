package com.desertfox.couplelink.model.responses

import com.google.gson.annotations.SerializedName

data class IndexRangeModel(
    @SerializedName("startIndex")
    val startIndex: Int,
    @SerializedName("endIndex")
    val endIndex: Int
)