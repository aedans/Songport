package com.ackdevelopment.songport

import com.github.kittinunf.fuel.httpGet
import com.github.kittiunf.fuel.jackson.*
import kotlinx.coroutines.experimental.CommonPool
import java.io.File
import java.util.concurrent.Executors

suspend fun async(fn: suspend () -> Unit) = kotlinx.coroutines.experimental.run(CommonPool, block = fn)

fun String.readText() = File(this).readText()

data class IpifiyResult(val ip: String? = null)

fun externalIp() =
        "https://api.ipify.org?format=json".httpGet()
                .responseObject<IpifiyResult>(jacksonDeserializerOf())
                .third
                .component1()?.ip ?: throw Exception("unable to get external ip")

val songport_url = externalIp()
