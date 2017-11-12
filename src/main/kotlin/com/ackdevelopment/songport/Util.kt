package com.ackdevelopment.songport

import kotlinx.coroutines.experimental.CommonPool
import java.io.File
import java.util.concurrent.Executors

suspend fun async(fn: suspend () -> Unit) = kotlinx.coroutines.experimental.run(CommonPool, block = fn)

/* TODO get external IP */
val songport_url = "127.0.0.1"

fun String.readText() = File(this).readText()
