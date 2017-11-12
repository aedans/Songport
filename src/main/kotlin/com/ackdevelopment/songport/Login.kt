package com.ackdevelopment.songport

import com.ackdevelopment.songport.models.User
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.auth.OAuthAccessTokenResponse
import org.jetbrains.ktor.auth.authentication
import org.jetbrains.ktor.client.DefaultHttpClient
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.oauthAtLocation
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.sessions.sessions
import org.jetbrains.ktor.sessions.set

@location("/login/{type}")
class Login(val type: String)

suspend fun ApplicationCall.redirectToLogin() = respondRedirect("/login.html")

fun Routing.login() {
    location<Login> {
        authentication {
            oauthAtLocation<Login>(
                    DefaultHttpClient,
                    exec,
                    providerLookup = { loginProviders[it.type] },
                    urlProvider = { _, settings -> "http://localhost/login/${settings.name}" }
            )
        }

        handle {
            val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
            if (principal != null) {
                val userId = principal.accessToken
                call.sessions.set(SongportSession(userId))
                putUserIfMissing(User(userId, emptyList()))

                call.respondRedirect("/user")
            } else {
                call.respondRedirect("/login_failure.html")
            }
        }
    }
}
