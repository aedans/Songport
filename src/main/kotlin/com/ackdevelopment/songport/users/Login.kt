package com.ackdevelopment.songport.users

import com.ackdevelopment.songport.SongportSession
import com.ackdevelopment.songport.digest
import com.ackdevelopment.songport.getUser
import com.ackdevelopment.songport.sites.links
import com.ackdevelopment.songport.sites.songportHtml
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.post
import org.jetbrains.ktor.request.receive
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.sessions.sessions
import org.jetbrains.ktor.sessions.set
import org.jetbrains.ktor.util.ValuesMap

@location("/login")
class Login

fun Routing.login() {
    get("/login.html") {
        call.respondText(getLoginHtml(), ContentType.Text.Html)
    }

    post<Login> {
        val login = call.receive<ValuesMap>()
        val username = login["username"]!!
        val password = login["password"]!!

        val passwordDigest = digest(password)

        val user = getUser(username)

        if (user != null && passwordDigest == user.password) {
            call.sessions.set(SongportSession(username + ":" + passwordDigest))

            call.respondRedirect("/user")
        } else {
            call.respondRedirect("/login_failure.html")
        }
    }
}

fun getLoginHtml() = songportHtml("Login") {
    form(action = "/login", method = FormMethod.post) {
        +"Username:"
        br
        input(type = InputType.text, name = "username") {

        }
        br
        +"Password:"
        br
        input(type = InputType.password, name = "password") {

        }

        input(type = InputType.submit, name = "submit") {

        }
    }
}
