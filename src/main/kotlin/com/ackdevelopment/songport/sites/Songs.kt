package com.ackdevelopment.songport.sites

import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.routing.Routing

@location("/song/{id}")
class Song(val id: String)

fun Routing.songs() {
    get<Song> {

    }
}
