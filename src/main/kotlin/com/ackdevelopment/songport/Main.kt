@file:JvmName("Main")

package com.ackdevelopment.songport

import com.ackdevelopment.songport.sites.*
import com.ackdevelopment.songport.users.login
import com.ackdevelopment.songport.users.register
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

data class SongportSession(val userId: String)

fun SongportSession.getUsernameAndPassword() = userId.split(':').let { (a, b) -> a to b }
fun SongportSession.getUsername() = getUsernameAndPassword().first
fun SongportSession.getPassword() = getUsernameAndPassword().second

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
        imgs()
        styles()
        index()
        register()
        login()
        songs()
        user()
        userEdit()
        users()
        playlists()
        services()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, module = { install() }).start()
}
