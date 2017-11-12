package com.ackdevelopment.songport.sites

import kotlinx.html.a
import kotlinx.html.br
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing

@location("/")
class Index

fun Routing.index() {
    get<Index> {
        call.respondText(getIndexHtml(), ContentType.Text.Html)
    }
}

fun getIndexHtml() = songportHtml("Songport") {
    a(href = "/register.html") {
        +"register"
    }

    br

    a(href = "/login.html") {
        +"login"
    }
}
