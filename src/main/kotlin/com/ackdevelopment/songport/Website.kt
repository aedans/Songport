package com.ackdevelopment.songport

import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get

fun Routing.website() {
    get("/{path...}") {
        async {
            val params = call.parameters.getAll("path")!!
            val path = params.joinToString(prefix = "", postfix = "", separator = "/")
            call.respondText("$WEBSITE/$path".readText(), ContentType.Text.Html)
        }
    }
}
