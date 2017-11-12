package com.ackdevelopment.songport

import com.ackdevelopment.songport.services.SpotifyService
import org.jetbrains.ktor.http.HttpStatusCode
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.get
import org.jetbrains.ktor.sessions.sessions

@location("/services/{type}/auth")
data class ServiceAuth(val type: String)

typealias AuthRedirectURL = String
typealias ClientID = String

typealias LoginHandler = (ClientID) -> AuthRedirectURL

val serviceLoginHandlers = mapOf<String, LoginHandler>(
        "spotify" to { _ ->
            SpotifyService.getAuthenticationURL(
                    "spotify-clientId.secret".readText(),
                    "spotify-client-secret.secret".readText(),
                    "http://$songportURL/services/spotify/auth")
        }
)

fun Routing.services() {
    get<ServiceAuth> {
        val service = it.type
        val session = call.sessions.get<SongportSession>()!!
        call.request.queryParameters["code"]?.let {
            /* this is after the redirect back from authentication */
            println("The user's code is $it")
            call.respondRedirect("/user/edit")
        } ?: run {
            serviceLoginHandlers[service]?.invoke(session.userId)?.let {
                call.respondRedirect(it)
                /* DO API STUFF NOW */
                call.respondRedirect("/")
            } ?: run {
                call.response.status(HttpStatusCode.NotFound)
                call.respondRedirect("/service_failure.html")
            }
        }
    }
}