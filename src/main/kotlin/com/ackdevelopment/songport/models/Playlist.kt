package com.ackdevelopment.songport.models

import java.io.Serializable

data class Playlist(
        val name: String,
        val songIds: List<String>
): Serializable