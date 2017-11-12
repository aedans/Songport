@file:JvmName("Main")

package com.ackdevelopment.songport

import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.locations.Locations
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.*
import org.jetbrains.ktor.util.hex

const val WEBSITE = "./website"

data class SongportSession(val userId: String)

fun ApplicationCall.getSession() = sessions.get<SongportSession>()

fun Application.install() {
    val hashKey = hex("6819b57a326945c1968f45236589")

    install(DefaultHeaders)
    install(CallLogging)
    install(Locations)
    install(Sessions) {
        cookie<SongportSession>("SESSION") {
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
        }
    }
    install(Routing) {
        index()
        login()
        users()
        website()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, module = { install() }).start()
}
