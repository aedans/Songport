package com.ackdevelopment.songport

import org.jetbrains.ktor.auth.OAuthAccessTokenResponse
import org.jetbrains.ktor.auth.authentication
import org.jetbrains.ktor.client.DefaultHttpClient
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.oauthAtLocation
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.param
import org.jetbrains.ktor.sessions.sessions
import org.jetbrains.ktor.sessions.set

@location("/login/{type?}")
class Login(val type: String = "")

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

        param("error") {
            handle {
                call.respondRedirect("/login_failed.html")
            }
        }

        handle {
            val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
            if (principal != null) {
                val userId = principal.accessToken
                call.sessions.set(SongportSession(userId))

                call.respondRedirect("/login_success.html")
            } else {
                call.respondRedirect("/login.html")
            }
        }
    }
}
