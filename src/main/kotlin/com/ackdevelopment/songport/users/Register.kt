package com.ackdevelopment.songport.users

import com.ackdevelopment.songport.SongportSession
import com.ackdevelopment.songport.digest
import com.ackdevelopment.songport.getUser
import com.ackdevelopment.songport.models.User
import com.ackdevelopment.songport.putUser
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

@location("/register")
class Register

fun Routing.register() {
    get("/register.html") {
        call.respondText(getRegisterHtml(null), ContentType.Text.Html)
    }

    post<Register> {
        val registration = call.receive<ValuesMap>()
        val username = registration["username"]!!
        val password = registration["password"]!!

        val passwordDigest = digest(password)

        val user = getUser(username)

        if (user != null) {
            call.respondText(getRegisterHtml("User $username already exists"), ContentType.Text.Html)
        } else {
            putUser(User(username, passwordDigest, emptyList(), null))

            call.sessions.set(SongportSession(username + ":" + passwordDigest))

            call.respondRedirect("/user")
        }
    }
}

fun getRegisterHtml(err: String?) = songportHtml("Register") {
    div(classes = "centered") {
        form(action = "/register", method = FormMethod.post) {
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
