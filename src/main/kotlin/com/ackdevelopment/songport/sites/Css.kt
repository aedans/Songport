package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.readText
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

val STYLES = "styles"

@location("/styles/{name}")
class Styles(val name: String)

fun Routing.styles() {
    get<Styles> {
        call.respondText("$STYLES/${it.name}".readText(), ContentType.Text.CSS)
    }
}
