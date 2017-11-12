package com.ackdevelopment.songport.users

import com.ackdevelopment.songport.SongportSession
import com.ackdevelopment.songport.digest
import com.ackdevelopment.songport.getUser
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.post
import org.jetbrains.ktor.request.receive
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.sessions
import org.jetbrains.ktor.sessions.set
import org.jetbrains.ktor.util.ValuesMap

@location("/login")
class Login

suspend fun ApplicationCall.redirectToLogin() = respondRedirect("/login.html")

fun Routing.login() {
    post<Login> {
        val login = call.receive<ValuesMap>()
        val username = login["username"]!!
        val password = login["password"]!!

        val user = getUser(username)
        val digestedPassword = digest(password)

        if (user != null && digestedPassword == user.password) {
            call.sessions.set(SongportSession(password))

            call.respondRedirect("/user")
        } else {
            call.respondRedirect("/login_failure.html")
        }
    }
}
