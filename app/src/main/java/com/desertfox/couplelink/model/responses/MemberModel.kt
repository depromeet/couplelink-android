package com.desertfox.couplelink.model.responses

data class MemberModel(
    val connectionNumber: String,
    val coupleId: Int,
    val id: Int,
    val name: String,
    val profileImageUrl: String,
    val status: String
)