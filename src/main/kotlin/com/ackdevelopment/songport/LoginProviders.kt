package com.ackdevelopment.songport

import org.jetbrains.ktor.auth.OAuthServerSettings
import org.jetbrains.ktor.http.HttpMethod
import java.io.File

val loginProviders = listOf(
        OAuthServerSettings.OAuth2ServerSettings(
                name = "google",
                authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                accessTokenUrl = "https://www.googleapis.com/oauth2/v3/token",
                requestMethod = HttpMethod.Post,
                defaultScopes = listOf("https://www.googleapis.com/auth/plus.login"),

                clientId = File("googleClientId.secret").readText(),
                clientSecret = File("googleClientSecret.secret").readText()
        )
).associateBy { it.name }
