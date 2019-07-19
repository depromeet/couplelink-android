package com.desertfox.couplelink.network

object StompUrl {
    const val BASE_URL = "http://54.180.152.196:8080"
    const val OPEN_STOMP = "$BASE_URL/api/websocket"

    fun sendMsg(coupleId: Int, roomId: Int) =
        String.format("/app/couples/%d/rooms/%d/messages", coupleId, roomId)

    fun receiveMsg(coupleId: Int, roomId: Int) =
        String.format("/topic/couples/%d/rooms/%d", coupleId, roomId)
}