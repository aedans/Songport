package com.ackdevelopment.songport.sites

import com.ackdevelopment.songport.getUser
import com.ackdevelopment.songport.models.User
import kotlinx.html.*
import kotlinx.html.stream.createHTML
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/users/{id}")
class Users(val id: String)

fun Routing.users() {
    get<Users> {
        val user = getUser(it.id)!!
        call.respondText(getUserHtml(user), ContentType.Text.Html)
    }
}

fun getUserHtml(user: User) = createHTML().html {
    head {
        title(user.title.capitalize())
        link(rel = "stylesheet", type = "text/css", href = "styles/songport.css") {

        }
    }

    body {
        h1 {
            +user.title.capitalize()
        }
    }
}
