package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.TEMPLATES
import com.ackdevelopment.songport.getPlaylist
import com.ackdevelopment.songport.models.Playlist
import com.ackdevelopment.songport.readText
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/playlists/{id}")
class Playlists(val id: String)

fun Routing.playlists() {
    get<Playlists> {
        val playlist = getPlaylist(it.id)!!
        call.respondText(getPlaylistHtml(playlist), ContentType.Text.Html)
    }
}

fun getPlaylistHtml(playlist: Playlist) = "$TEMPLATES/playlist.html".readText()
        .replace("%PLAYLIST_TITLE%", playlist.title)
