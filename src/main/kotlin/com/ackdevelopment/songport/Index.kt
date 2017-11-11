package com.ackdevelopment.songport

import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get

const val INDEX = "$WEBSITE/INDEX.html"

fun Routing.index() {
    get("/") {
        async {
            call.respondText(INDEX.readText(), ContentType.Text.Html)
        }
    }
}
