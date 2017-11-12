package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.TEMPLATES
import com.ackdevelopment.songport.getSong
import com.ackdevelopment.songport.models.Song
import com.ackdevelopment.songport.readText
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

fun getSongHtml(song: Song) = "$TEMPLATES/song.html".readText()
        .replace("%SONG_TITLE%", song.title)
