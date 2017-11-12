package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.WEBSITE
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import java.io.File

fun Routing.website() {
    loadAll(File(WEBSITE), "")
}

fun Routing.loadAll(file: File, acc: String) {
    if (file.isDirectory) {
        file.listFiles().forEach {
            loadAll(it, acc + "/${it.name}")
        }
    } else {
        get(acc) {
            call.respondText(file.readText(), ContentType.Text.Html)
        }
    }
}
