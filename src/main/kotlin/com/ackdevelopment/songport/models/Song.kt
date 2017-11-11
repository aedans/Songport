package com.ackdevelopment.songport.models

import java.io.Serializable

data class Song(val title: String, val album: String, val artist: String, val seconds: Long, val services: List<String>): Serializable
