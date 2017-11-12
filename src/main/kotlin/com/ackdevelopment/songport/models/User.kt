package com.ackdevelopment.songport.models

import java.io.Serializable

data class User(
        val _id: String,
        val name: String,
        val playlistIds: List<String>
): Serializable
