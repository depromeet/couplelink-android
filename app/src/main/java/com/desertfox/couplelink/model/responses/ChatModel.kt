package com.desertfox.couplelink.model.responses

import com.desertfox.couplelink.chatting.MsgType

data class ChatModel(
    val msgModel: MsgModel,
    val msgType: MsgType
)