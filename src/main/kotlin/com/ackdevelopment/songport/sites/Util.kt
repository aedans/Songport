package com.ackdevelopment.songport.sites

import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun songportHtml(title: String) = createHTML().html {
    head {
        this.title(title)
        links()
    }

    body {

    }
}

fun HEAD.links() {
    link(rel = "stylesheet", type = "text/css", href = "/styles/normalize.css") {

    }

    link(rel = "stylesheet", type = "text/css", href = "/styles/skeleton.css") {

    }

    link(rel = "stylesheet", type = "text/css", href = "/styles/songport.css") {

    }
}
