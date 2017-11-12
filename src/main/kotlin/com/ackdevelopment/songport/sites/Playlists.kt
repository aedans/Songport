package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.getPlaylist
import com.ackdevelopment.songport.models.Playlist
import kotlinx.html.h1
import kotlinx.html.style
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/playlists/{id}")
class Playlists(val id: String)

fun Routing.playlists() {
    get<Playlists> {
        val playlist = getPlaylist(it.id)
        if (playlist == null) {
            call.respondCouldNotFind("playlist", it.id)
        } else {
            call.respondText(getPlaylistHtml(playlist), ContentType.Text.Html)
        }
    }
}

fun getPlaylistHtml(playlist: Playlist) = songportHtml(playlist.title) {
    h1 {
        style = "text-align: center"
        +playlist.title.capitalize()
    }
}
