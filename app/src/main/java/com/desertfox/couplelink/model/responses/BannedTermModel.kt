package com.desertfox.couplelink.model.responses

data class BannedTermModel(
    val createdAt: String,
    val id: Int,
    val name: String,
    val receiver: MemberModel,
    val writer: MemberModel
)