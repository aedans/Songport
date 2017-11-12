package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.getUser
import com.ackdevelopment.songport.models.User
import com.ackdevelopment.songport.models.title
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.style
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/users/{id}")
class Users(val id: String)

fun Routing.users() {
    get<Users> {
        val user = getUser(it.id)
        if (user == null) {
            call.respondCouldNotFind("user", it.id)
        } else {
            call.respondText(getUserHtml(user), ContentType.Text.Html)
        }
    }
}

fun getUserHtml(user: User) = songportHtml(user.title.capitalize()) {
    h1 {
        style = "text-align: center"
        +user.title.capitalize()
    }
}
