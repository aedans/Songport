package com.ackdevelopment.songport

import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.sessions.get
import org.jetbrains.ktor.sessions.sessions

fun Routing.users() {
    userPlaylists()
    userPlaylist()
}

fun Routing.userPlaylists() {
    get("/user/playlists") {
        val session = call.sessions.get<SongportSession>()
        if (session == null) {
            call.redirectToLogin()
        } else {
            val user = getUser(session.userId)!!
            val playlists = user.playlistIds.map { getPlaylist(it)!! }
            println(playlists)
        }
    }
}

@location("/user/playlist/{pid}")
class UserPlaylist(val pid: String)

fun Routing.userPlaylist() {
    get<UserPlaylist> {
        val session = call.sessions.get<SongportSession>()!!
        val user = getUser(session.userId)!!

    }
}
