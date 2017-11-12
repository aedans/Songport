package com.ackdevelopment.songport.models

import com.ackdevelopment.songport.digest
import java.io.Serializable

data class Song(
        val title: String,
        val album: String,
        val artist: String,
        val seconds: Long,
        val services: List<String>,
        val _id: String = digest(title + album + artist + seconds + services.toString())
): Serializable
