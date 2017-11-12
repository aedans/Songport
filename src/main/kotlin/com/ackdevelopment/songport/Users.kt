package com.ackdevelopment.songport

import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.routing.Routing

fun Routing.users() {
    userPlaylist()
}

@location("/user/playlist/{pid}")
class UserPlaylist(val pid: String)

fun Routing.userPlaylist() {
    get<UserPlaylist> {
        val session = call.getSession()!!
        val user = getUser(session.userId)!!

    }
}
