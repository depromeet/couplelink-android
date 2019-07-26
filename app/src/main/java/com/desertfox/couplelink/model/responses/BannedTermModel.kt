package com.desertfox.couplelink.model.responses

data class BannedTermModel(
    val count:Int,
    val createdAt: String,
    val id: Int,
    val name: String,
    val writer: MemberModel
)