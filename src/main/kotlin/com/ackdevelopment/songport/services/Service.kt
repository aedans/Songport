package com.ackdevelopment.songport.services

import com.ackdevelopment.songport.models.*

interface Service {
    /* Playlist methods */
    fun createPlaylist(name: String)
    fun removePlaylist(name: String)
    fun updatePlaylist(name: String, songs: List<Song>)
    fun findPlaylist(name: String): Playlist

    /* Library methods */
    fun findSong(name: String): Song
    fun addSong(name: String)
    fun removeSong(name: String)
}
