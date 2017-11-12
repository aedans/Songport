package com.ackdevelopment.songport.users

import com.ackdevelopment.songport.SongportSession
import com.ackdevelopment.songport.digest
import com.ackdevelopment.songport.getUser
import com.ackdevelopment.songport.sites.songportHtml
import kotlinx.html.*
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
        call.respondText(getLoginHtml(null), ContentType.Text.Html)
    }

    post<Login> {
        val login = call.receive<ValuesMap>()
        val username = login["username"]!!
        val password = login["password"]!!

        val passwordDigest = digest(password)

        val user = getUser(username)

        when {
            user == null -> call.respondText(getLoginHtml("Could not find user $username"), ContentType.Text.Html)
            passwordDigest != user.password -> call.respondText(getLoginHtml("Incorrect password"), ContentType.Text.Html)
            else -> {
                call.sessions.set(SongportSession(username + ":" + passwordDigest))

                call.respondRedirect("/user")
            }
        }
    }
}

fun getLoginHtml(err: String?) = songportHtml("Login") {
    div(classes = "centered") {
        form(action = "/login", method = FormMethod.post) {
            if (err != null) {
                p {
                    style = "color: red"
                    +err
                }
            }

            +"Username:"
            br
            input(type = InputType.text, name = "username") {

            }
            br
            +"Password:"
            br
            input(type = InputType.password, name = "password") {

            }
            br
            input(type = InputType.submit, name = "submit") {

            }
        }
    }
}
