package com.ackdevelopment.songport

import kotlinx.html.*
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.auth.OAuthAccessTokenResponse
import org.jetbrains.ktor.auth.authentication
import org.jetbrains.ktor.client.DefaultHttpClient
import org.jetbrains.ktor.html.respondHtml
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.oauthAtLocation
import org.jetbrains.ktor.routing.Routing

@location("/login/{type?}")
class Login(val type: String = "")

fun Routing.login() {
    location<Login> {
        authentication {
            oauthAtLocation<Login>(
                    DefaultHttpClient,
                    exec,
                    providerLookup = { loginProviders[it.type] },
                    urlProvider = { login, settings -> "http://localhost/" }
            )
        }

        handle {
            val principal = call.authentication.principal<OAuthAccessTokenResponse>()
            if (principal != null) {
//                call.loggedInSuccessResponse(principal)
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
