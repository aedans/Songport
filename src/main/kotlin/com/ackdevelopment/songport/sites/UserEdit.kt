package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.SongportSession
import com.ackdevelopment.songport.getUser
import com.ackdevelopment.songport.models.User
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.get
import org.jetbrains.ktor.sessions.sessions

@location("/user/edit")
class UserEdit

fun Routing.userEdit() {
    get<UserEdit> {
        val session = call.sessions.get<SongportSession>()!!
        val (username, passwordDigest) = session.userId.split(':')
        val user = getUser(username)!!
        if (passwordDigest == user.password) {
            call.respondText(getUserEditHtml(user), ContentType.Text.Html)
        }
    }
}

fun getUserEditHtml(user: User) = createHTML().html {
    head {
        title(user.title.capitalize())
        links()
    }

    body {
        h1 {
            +"Edit ${user.title.capitalize()}"
        }
    }
}
