package com.ackdevelopment.songport.sites

import org.jetbrains.ktor.content.files
import org.jetbrains.ktor.content.static
import org.jetbrains.ktor.routing.Routing

fun Routing.imgs() {
    static("imgs") {
        files("imgs")
    }
}
