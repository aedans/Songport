package com.ackdevelopment.songport

import org.jetbrains.ktor.auth.OAuthServerSettings
import org.jetbrains.ktor.http.HttpMethod

val loginProviders = listOf(
        OAuthServerSettings.OAuth2ServerSettings(
                name = "google",
                authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                accessTokenUrl = "https://www.googleapis.com/oauth2/v3/token",
                requestMethod = HttpMethod.Post,
                defaultScopes = listOf("https://www.googleapis.com/auth/plus.login"),

                clientId = "267081944557-prc21n3ps4b10dhcfboeg06q89hdp26a.apps.googleusercontent.com",
                clientSecret = "fR19It_Nungd7oKivIJZcurN"
        )
).associateBy { it.name }
