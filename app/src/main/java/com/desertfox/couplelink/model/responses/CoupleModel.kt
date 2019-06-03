package com.desertfox.couplelink.model.responses

data class CoupleModel(
    val chatRoom: ChatRoomModel,
    val connectionStatus: String,
    val id: Int,
    val members: List<MemberModel>,
    val startedAt: String
)