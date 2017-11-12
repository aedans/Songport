package com.ackdevelopment.songport.services

import com.ackdevelopment.songport.models.*;

class SpotifyService(val token: String): Service {

    override fun createPlaylist(name: String) {
        TODO("not implemented")
    }

    override fun removePlaylist(name: String) {
        TODO("not implemented")
    }

    override fun updatePlaylist(name: String, songs: List<Song>) {
        TODO("not implemented")
    }

    override fun findPlaylist(name: String): Playlist {
        TODO("not implemented")
    }

    override fun findSong(name: String): Song {
        TODO("not implemented")
    }

    override fun addSong(name: String) {
        TODO("not implemented")
    }

    override fun removeSong(name: String) {
        TODO("not implemented")
    }
}