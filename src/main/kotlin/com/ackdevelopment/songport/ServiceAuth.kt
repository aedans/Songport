package com.ackdevelopment.songport

import com.ackdevelopment.songport.services.SpotifyService
import org.jetbrains.ktor.http.HttpStatusCode
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.get
import org.jetbrains.ktor.sessions.sessions
import org.litote.kmongo.updateOneById

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
        val username = session.getUsername()
        call.request.queryParameters["code"]?.let {
            /* this is after the redirect back from authentication */
            println("The user's code is $it")
            println("Storing the code in the database")
            getUser(username)?.copy(spotifyAuthCode = it)?.let { database.getCollection("users").updateOneById(it._id, it) }
                ?: throw Exception("user $username does not exist")
            call.respondRedirect("/user/edit")
        } ?: run {
            serviceLoginHandlers[service]?.invoke(username)?.let {
                call.respondRedirect(it)
            } ?: run {
                call.response.status(HttpStatusCode.NotFound)
                call.respondRedirect("/service_failure.html")
            }
        }
    }
}