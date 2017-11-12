package com.ackdevelopment.songport

import org.jetbrains.ktor.util.encodeBase64
import org.jetbrains.ktor.util.getDigestFunction
import java.io.File

fun String.readText() = File(this).readText()

val songportURL = "198.199.116.233"

val digest = { it: String ->
    encodeBase64(getDigestFunction("MD5", "")(it)).replace("/", "%${'/'.toInt()}")
}
