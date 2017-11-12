package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.WEBSITE
import com.ackdevelopment.songport.async
import com.ackdevelopment.songport.readText
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

const val INDEX = "$WEBSITE/index.html"

@location("/")
class Index

fun Routing.index() {
    get<Index> {
        async {
            call.respondText(INDEX.readText(), ContentType.Text.Html)
        }
    }
}
