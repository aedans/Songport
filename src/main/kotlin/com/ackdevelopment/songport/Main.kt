@file:JvmName("Main")

package com.ackdevelopment.songport

import com.mongodb.client.MongoDatabase
import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.features.CallLogging
import org.jetbrains.ktor.features.DefaultHeaders
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.locations.Locations
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.SessionTransportTransformerMessageAuthentication
import org.jetbrains.ktor.sessions.Sessions
import org.jetbrains.ktor.sessions.cookie
import org.jetbrains.ktor.util.hex
import org.litote.kmongo.KMongo

const val WEBSITE = "./website"

fun Application.install(db: MongoDatabase) {
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
        login(db)
        index()
        website()
    }
}

fun main(args: Array<String>) {
    val db = KMongo.createClient().getDatabase("users")
    embeddedServer(Netty, module = { install(db) }).start()
}
