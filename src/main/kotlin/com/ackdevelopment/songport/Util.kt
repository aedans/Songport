package com.ackdevelopment.songport

import kotlinx.coroutines.experimental.CommonPool
import java.io.File
import java.util.concurrent.Executors

suspend fun async(fn: suspend () -> Unit) = kotlinx.coroutines.experimental.run(CommonPool, block = fn)

fun String.readText() = File(this).readText()

val exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 4)
