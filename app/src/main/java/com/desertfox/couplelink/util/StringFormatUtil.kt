package com.desertfox.couplelink.util


object StringFormatUtil {

    fun parseLocalDateTime(timestamp: String): String =
        timestamp.replace("T", "-").replace(":", "-")

    fun getDateString(localDateTime: String): String {
        val formattedDate = parseLocalDateTime(localDateTime)
        val date = formattedDate.split("-")
        return String.format("%s년 %d월 %d일", date[0], date[1].toInt(), date[2].toInt())
    }

    fun getTimeString(localDateTime: String): String {
        val formattedDate = parseLocalDateTime(localDateTime)
        val time = formattedDate.split("-")
        val hour = time[3].toInt()
        val minute = time[4].toInt()

        return if (hour > 13) {
            String.format("오후 %d:%d", hour - 12, minute)
        } else {
            String.format("오전 %d:%d", hour, minute)
        }
    }
}