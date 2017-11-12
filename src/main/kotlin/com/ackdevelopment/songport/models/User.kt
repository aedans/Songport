package com.ackdevelopment.songport.models

import java.io.Serializable

data class User(
        val _id: String,
        val password: String,
        val playlistIds: List<String>
): Serializable
