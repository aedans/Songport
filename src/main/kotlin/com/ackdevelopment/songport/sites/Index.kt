package com.ackdevelopment.songport.sites

import kotlinx.html.*
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.get
import org.jetbrains.ktor.routing.Routing

fun Routing.index() {
    get("/") {
        call.respondText(getIndexHtml(), ContentType.Text.Html)
    }
}

fun getIndexHtml() = songportHtml("Songport") {
    div(classes = "centered") {
        /*
        img(src = "/imgs/logo.jpg") {

        }
        */

        br

        a(classes = "button", href = "/register.html") {
            +"register"
        }

        br

        a(classes = "button", href = "/login.html") {
            +"login"
        }
    }
}
