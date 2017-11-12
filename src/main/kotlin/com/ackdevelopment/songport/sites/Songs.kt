package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.getSong
import com.ackdevelopment.songport.models.Song
import kotlinx.html.br
import kotlinx.html.h1
import kotlinx.html.style
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/songs/{id}")
class Songs(val id: String)

fun Routing.songs() {
    get<Songs> {
        val song = getSong(it.id)
        if (song == null) {
            call.respondCouldNotFind("song", it.id)
        } else {
            call.respondText(getSongHtml(song), ContentType.Text.Html)
        }
    }
}

fun getSongHtml(song: Song) = songportHtml(song.title) {
    h1 {
        style = "text-align: center"
        +song.title.capitalize()
    }

    br

    +"Artist: ${song.artist}"
    br
    +"Album: ${song.album}"
    br
    +"Time: ${song.seconds / 60}:${song.seconds % 60}"
    br
    +"Services: ${song.services.joinToString(prefix = "", postfix = "")}"
}
