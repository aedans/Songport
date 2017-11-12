package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.SongportSession
import com.ackdevelopment.songport.apiMap
import com.ackdevelopment.songport.getUser
import com.ackdevelopment.songport.getUsernameAndPassword
import com.ackdevelopment.songport.models.User
import com.ackdevelopment.songport.models.title
import com.ackdevelopment.songport.services.SpotifyService
import kotlinx.html.*
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.get
import org.jetbrains.ktor.sessions.sessions

@location("/user/edit")
class UserEdit

fun Routing.userEdit() {
    get<UserEdit> {
        val session = call.sessions.get<SongportSession>()
        if (session == null) {
            call.respondRedirect("/login.html")
        } else {
            val (username, passwordDigest) = session.getUsernameAndPassword()
            val user = getUser(username)
            if (user == null) {
                call.respondCouldNotFind("user", username)
            } else {
                if (passwordDigest == user.password) {
                    call.respondText(getUserEditHtml(user), ContentType.Text.Html)
                }
            }
        }
    }
}

fun getUserEditHtml(user: User) = songportHtml(user.title.capitalize()) {
    div(classes = "centered") {
        h1 {
            +"Edit ${user.title.capitalize()}"
        }

        +"Import from Spotify"
        br
        form {
            action = "/services/spotify/auth"
            method = FormMethod.get
            input {
                type = InputType.submit
                style = "background-color: #6AE368; color: black"
            }
        }
    }
}
