package com.ackdevelopment.songport.sites

import kotlinx.html.*
import kotlinx.html.stream.createHTML

fun songportHtml(title: String, body: DIV.() -> Unit) = createHTML().html {
    head {
        this.title(title)
        links()
    }

    body {
        div {
            style = "margin: 1em"
            body()
        }
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
