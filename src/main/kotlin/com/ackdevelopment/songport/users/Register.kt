package com.ackdevelopment.songport.users

import com.ackdevelopment.songport.digest
import com.ackdevelopment.songport.models.User
import com.ackdevelopment.songport.putUser
import org.jetbrains.ktor.locations.location
import org.jetbrains.ktor.locations.post
import org.jetbrains.ktor.request.receive
import org.jetbrains.ktor.routing.Routing
import org.jetbrains.ktor.util.ValuesMap

@location("/register")
class Register

fun Routing.register() {
    post<Register> {
        val registration = call.receive<ValuesMap>()
        val username = registration["username"]!!
        val password = registration["password"]!!

        putUser(User(username, digest(password), emptyList()))
    }
}
