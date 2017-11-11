@file:JvmName("Main")

package com.ackdevelopment.songport

import org.jetbrains.ktor.application.Application
import org.jetbrains.ktor.application.install
import org.jetbrains.ktor.host.embeddedServer
import org.jetbrains.ktor.http.ContentType
import org.jetbrains.ktor.netty.Netty
import org.jetbrains.ktor.response.respondText
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.get
import java.io.File

fun Application.module() {
    install(Routing) {
        get("/") {
            call.respondText(File("./website/index.html").readText(), ContentType.Text.Html)
        }

        get("/{path...}") {
            val params = call.parameters.getAll("path")!!
            val path = params.joinToString(prefix = "", postfix = "", separator = "/")
            call.respondText(File("./website", path).readText(), ContentType.Text.Html)
        }
    }
}

fun main(args: Array<String>) {
    embeddedServer(Netty, module = Application::module).start()
}
