package com.ackdevelopment.songport

import kotlinx.coroutines.experimental.CommonPool
import java.io.File

suspend fun async(fn: suspend () -> Unit) = kotlinx.coroutines.experimental.run(CommonPool, block = fn)

fun String.readText() = File(this).readText()
