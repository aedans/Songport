package com.ackdevelopment.songport

import com.github.kittinunf.fuel.httpGet
import com.github.kittiunf.fuel.jackson.jacksonDeserializerOf
import kotlinx.coroutines.experimental.CommonPool
import org.jetbrains.ktor.util.getDigestFunction
import java.io.File
import java.nio.charset.Charset

suspend fun async(fn: suspend () -> Unit) = kotlinx.coroutines.experimental.run(CommonPool, block = fn)

fun String.readText() = File(this).readText()

data class IpifyResult(val ip: String? = null)

fun externalIp() =
        "https://api.ipify.org?format=json".httpGet()
                .responseObject<IpifyResult>(jacksonDeserializerOf())
                .third
                .component1()?.ip ?: throw Exception("unable to get external ip")

val songportURL = externalIp()

val digest = { it: String -> getDigestFunction("MD5", "")(it).toString(Charset.defaultCharset()) }
