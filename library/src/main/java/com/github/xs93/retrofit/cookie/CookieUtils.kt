package com.github.xs93.retrofit.cookie

import okhttp3.Cookie
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import kotlin.experimental.and

/**
 *
 *
 *
 * @author xushuai
 * @date   2022/9/2-23:53
 * @email  466911254@qq.com
 */

fun String.toByteArray(): ByteArray {
    check(length % 2 == 0) { "Must have an even length" }
    return chunked(2)
        .map {
            it.toInt(16).toByte()
        }
        .toByteArray()
}

fun ByteArray.toHexString(): String {
    val hexArray = "0123456789ABCDEF".toCharArray()
    val hexChars = CharArray(size * 2)
    for (index in indices) {
        val v = (this[index] and 0x0f.toByte()).toInt()
        hexChars[index * 2] = hexArray[v ushr 4]
        hexChars[index * 2 + 1] = hexArray[v and 0x0f]
    }
    return String(hexChars)
}


fun String.toCookie(): Cookie? {
    val byteArray = toByteArray()
    val byteArrayInputStream = ByteArrayInputStream(byteArray)
    val cookie = try {
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        (objectInputStream.readObject() as SerializableCookie).getCookie()
    } catch (e: Exception) {
        null
    }
    return cookie
}

fun Cookie.toHexString(): String? {
    val serializableCookie = SerializableCookie(this)
    val baos = ByteArrayOutputStream()
    try {
        val oos = ObjectOutputStream(baos)
        oos.writeObject(serializableCookie)
    } catch (e: Exception) {
        return null
    }
    return baos.toByteArray().toHexString()
}


fun Cookie.isExpired(): Boolean {
    return expiresAt < System.currentTimeMillis()
}


val Cookie.token: String
    get() {
        return "$name@$domain"
    }