package com.ackdevelopment.songport

import com.ackdevelopment.songport.services.SpotifyApi
import com.ackdevelopment.songport.services.SpotifyService
import com.ackdevelopment.songport.sites.user
import org.jetbrains.ktor.locations.get
import org.jetbrains.ktor.locations.location
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

val clientID = "spotify-clientId.secret".readText()
val clientSecret = "spotify-client-secret.secret".readText()

val apiMap = mutableMapOf<String, SpotifyApi>()

fun spotifyHandler() = SpotifyService.getAuthenticationURL(
                        clientID,
                        clientSecret,
                        "http://$songportURL/services/spotify/auth")

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
            SpotifyService.privilegedInstance(username)
            apiMap[username]!!.mySavedTracks.build().get().items.forEach {
                println(it)
            }
            call.respondRedirect("/user/edit")
        } ?: run {
            when(service){
                "spotify" -> spotifyHandler().let {
                    apiMap[username] = it.first
                    call.respondRedirect(it.second)
                }
                else -> throw Exception("unknown service $service")
            }
        }
    }
}