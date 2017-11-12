package com.ackdevelopment.songport

import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.post
import org.jetbrains.ktor.request.receive
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.sessions
import org.jetbrains.ktor.sessions.set
import org.jetbrains.ktor.util.ValuesMap
import org.jetbrains.ktor.util.getDigestFunction
import java.nio.charset.Charset

@location("/login")
class Login

suspend fun ApplicationCall.redirectToLogin() = respondRedirect("/login.html")

val digest = { it: String -> getDigestFunction("MD5", "")(it).toString(Charset.defaultCharset()) }

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
