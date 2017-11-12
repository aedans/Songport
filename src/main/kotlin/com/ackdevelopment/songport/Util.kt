package com.ackdevelopment.songport

import org.jetbrains.ktor.util.getDigestFunction
import java.io.File
import java.nio.charset.Charset

fun String.readText() = File(this).readText()

val songportURL = "198.199.116.233"

val digest = { it: String -> getDigestFunction("MD5", "")(it).toString(Charset.defaultCharset()) }
