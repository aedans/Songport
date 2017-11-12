package com.ackdevelopment.songport.sites

import kotlinx.html.HEAD
import kotlinx.html.link

fun HEAD.links() {
    link(rel = "stylesheet", type = "text/css", href = "/styles/normalize.css") {
    }

    link(rel = "stylesheet", type = "text/css", href = "/styles/skeleton.css") {

    }

    link(rel = "stylesheet", type = "text/css", href = "/styles/songport.css") {

    }
}