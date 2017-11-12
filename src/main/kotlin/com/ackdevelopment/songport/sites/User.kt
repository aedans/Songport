package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.SongportSession
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.get
import org.jetbrains.ktor.sessions.sessions

@location("/user")
class User

fun Routing.user() {
    get<User> {
        val session = call.sessions.get<SongportSession>()
        if (session == null) {
            call.respondRedirect("/login.html")
        } else {
            val (username, _) = session.userId.split(':')
            call.respondRedirect("/users/$username")
        }
    }
}
