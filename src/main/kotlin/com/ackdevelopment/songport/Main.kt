@file:JvmName("Main")

package com.ackdevelopment.songport

import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.locations.Locations
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.routing.Routing

const val WEBSITE = "./website"

fun Application.module() {
    install(Locations)
    install(Routing) {
        login()
        index()
//        website()
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, module = Application::module).start()
}
