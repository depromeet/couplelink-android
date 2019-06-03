package com.desertfox.couplelink

import android.util.Log

private const val tag = "COUPLE_LINK"

fun d(msg: String) {
    val fullClassName = Thread.currentThread().stackTrace[3].className
    val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    Log.d(tag, "[$className.$methodName():$lineNumber] : $msg")
}

fun e(msg: String) {
    val fullClassName = Thread.currentThread().stackTrace[3].className
    val className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1)
    val methodName = Thread.currentThread().stackTrace[3].methodName
    val lineNumber = Thread.currentThread().stackTrace[3].lineNumber
    Log.e(tag, "[$className.$methodName():$lineNumber] : $msg")
}