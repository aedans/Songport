package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.getSong
import com.ackdevelopment.songport.models.Song
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/songs/{id}")
class Songs(val id: String)

fun Routing.songs() {
    get<Songs> {
        val song = getSong(it.id)!!
        call.respondText(getSongHtml(song), ContentType.Text.Html)
    }
}

fun getSongHtml(song: Song) = createHTML().html {
    head {
        title(song.title.capitalize())
        link(rel = "stylesheet", type = "text/css", href = "styles/songport.css") {

        }
    }

    body {
        h1 {
            +song.title.capitalize()
        }
    }
}
