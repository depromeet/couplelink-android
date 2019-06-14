package com.desertfox.couplelink.util

import android.util.Log

const val ACCESS_TOKEN = "ACCESS_TOKEN"
const val COUPLE_LINK = "COUPLE_LINK"

fun d(msg: Any) {
    val fullClassName = Thread.currentThread().stackTrace[3].className
    val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    Log.d(COUPLE_LINK, "[$className.$methodName():$lineNumber] : $msg")
}

fun e(msg: Any) {
    val fullClassName = Thread.currentThread().stackTrace[3].className
    val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    Log.e(COUPLE_LINK, "[$className.$methodName():$lineNumber] : $msg")
}