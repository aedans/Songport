package com.ackdevelopment.songport

import org.jetbrains.ktor.http.HttpStatusCode
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.get
import org.jetbrains.ktor.sessions.sessions

@location("/service/{type}/auth")
data class ServiceAuth(val type: String)

typealias AuthRedirectURL = String
typealias ClientID = String

typealias LoginHandler = (ClientID) -> AuthRedirectURL

val serviceLoginHandlers = mapOf<String, LoginHandler>(
        /* TODO actually add authenticator */
        "spotify" to { _ -> "/service_failure.html" }
)

fun Routing.services() {
    get<ServiceAuth> {
        val service = it.type
        val session = call.sessions.get<SongportSession>()!!
        println("service being used: $service")
        serviceLoginHandlers[service]?.invoke(session.userId)?.let {
            call.respondRedirect(it)
        } ?: run {
            call.response.status(HttpStatusCode.NotFound)
            call.respondRedirect("/service_failure.html")
        }
    }
}