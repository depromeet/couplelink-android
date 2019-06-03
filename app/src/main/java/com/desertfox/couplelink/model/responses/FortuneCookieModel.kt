package com.desertfox.couplelink.model.responses

data class FortuneCookieModel(
    val createdAt: String,
    val id: Int,
    val message: String,
    val readAt: String,
    val status: String,
    val writer: MemberModel
)