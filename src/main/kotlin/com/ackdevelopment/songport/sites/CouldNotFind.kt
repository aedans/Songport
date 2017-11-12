package com.ackdevelopment.songport.sites

import kotlinx.html.a
import kotlinx.html.h1
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText

suspend fun ApplicationCall.respondCouldNotFind(type: String, name: String) =
        respondText(couldNotFindHtml(type, name), ContentType.Text.Html)

fun couldNotFindHtml(type: String, name: String) = songportHtml("Could not find $type $name") {
    h1 {
        +"Could not find $type $name"
    }

    a(href = "/") {
        +"Back to home"
    }
}
