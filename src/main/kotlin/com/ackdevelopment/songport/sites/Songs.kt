package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.TEMPLATES
import com.ackdevelopment.songport.getSong
import com.ackdevelopment.songport.readText
import org.jetbrains.ktor.http.ContentType
import com.ackdevelopment.songport.models.Song as SongInfo
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/songs/{id}")
class Song(val id: String)

fun Routing.songs() {
    get<Song> {
        val song = getSong(it.id)!!
        call.respondText(getSongHtml(song), ContentType.Text.Html)
    }
}

fun getSongHtml(song: SongInfo) = "$TEMPLATES/song.html".readText()
        .replace("%SONG_TITLE%", song.title)
