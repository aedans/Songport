@file:JvmName("Main")

package com.ackdevelopment.songport

import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.locations.Locations
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.Sessions
import org.jetbrains.ktor.sessions.cookie

const val WEBSITE = "./website"

data class SongportSession(val userId: String)

fun Application.install() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)
    install(Sessions) {
        cookie<SongportSession>("SESSION") {
            cookie.path = "/"
        }
    }
    install(Routing) {
        index()
        login()
        users()
        website()
        services()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, module = { install() }).start()
}
