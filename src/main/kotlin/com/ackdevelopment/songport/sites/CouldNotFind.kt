package com.ackdevelopment.songport.sites

import kotlinx.html.a
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.style
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText

suspend fun ApplicationCall.respondCouldNotFind(type: String, name: String) =
        respondText(couldNotFindHtml(type, name), ContentType.Text.Html)

fun couldNotFindHtml(type: String, name: String) = songportHtml("Could not find $type $name") {
    h1 {
        style = "text-align: center"
        +"Could not find $type $name"
    }

    a(classes = "centered button", href = "/") {
        +"Back to home"
    }
}
