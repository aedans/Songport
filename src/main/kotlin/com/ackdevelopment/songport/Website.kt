 package com.ackdevelopment.songport

import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/{path...}")
data class Website(val path: List<String>)

fun Routing.website() {
    get<Website> {
        async {
            val pathStr = it.path.joinToString(prefix = "", postfix = "", separator = "/")
            call.respondText("$WEBSITE/$pathStr".readText(), ContentType.Text.Html)
        }
    }
}
