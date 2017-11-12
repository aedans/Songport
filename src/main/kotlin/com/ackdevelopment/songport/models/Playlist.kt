package com.ackdevelopment.songport.models

import java.io.Serializable

data class Playlist(val name: String, val songs: List<Song>): Serializable