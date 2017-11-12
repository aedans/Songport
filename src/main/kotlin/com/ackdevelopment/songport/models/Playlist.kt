package com.ackdevelopment.songport.models

import com.ackdevelopment.songport.digest
import java.io.Serializable

data class Playlist(
        val title: String,
        val songIds: List<String>,
        val _id: String = digest(title + songIds.toString())
): Serializable
