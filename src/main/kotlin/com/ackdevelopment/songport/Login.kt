package com.ackdevelopment.songport

import com.mongodb.client.MongoDatabase
import kotlinx.html.*
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.auth.OAuthAccessTokenResponse
import org.jetbrains.ktor.auth.authentication
import org.jetbrains.ktor.client.DefaultHttpClient
import org.jetbrains.ktor.html.respondHtml
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.oauthAtLocation
import org.jetbrains.ktor.response.respondRedirect
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.routing.param
import org.jetbrains.ktor.sessions.sessions
import org.jetbrains.ktor.sessions.set

@location("/login/{type?}")
class Login(val type: String = "")

data class SongportSession(val userId: String)

fun Routing.login(db: MongoDatabase) {
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
                call.respondRedirect("login_failed.html")
            }
        }

        handle {
            val principal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
            if (principal != null) {
                val userId = principal.accessToken
                call.sessions.set(SongportSession(userId))

                call.loggedInSuccessResponse(principal.accessToken)
            } else {
                call.loginPage()
            }
        }
    }
}

private suspend fun ApplicationCall.loginPage() {
    respondHtml {
        head {
            title {
                +"Login with"
            }
        }

        body {
            h1 {
                +"Login with:"
            }

            for ((key, _) in loginProviders) {
                p {
                    a(href = "/login/$key") {
                        +key
                    }
                }
            }
        }
    }
}

private suspend fun ApplicationCall.loggedInSuccessResponse(token: String) {
    respondHtml {
        head {
            title { +"Logged in" }
        }

        body {
            h1 {
                +"You are logged in"
            }

            p {
                +"Your token is $token"
            }
        }
    }
}
