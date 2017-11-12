package com.ackdevelopment.songport.services

import com.ackdevelopment.songport.database
import com.ackdevelopment.songport.getUser
import com.ackdevelopment.songport.models.*;
import com.wrapper.spotify.Api
import org.jetbrains.ktor.application.ApplicationCall
import org.jetbrains.ktor.response.respondRedirect
import java.util.*

typealias SpotifyApi = com.wrapper.spotify.Api

class SpotifyService(private val userID: String, private val api: SpotifyApi): Service {

    val native get() = api

    private fun SpotifyApi.findPlaylist(name: String) =
            api.getPlaylistsForUser(userID).build().get().items.find { it.name == name }
            ?: throw Exception("Unable to find playlist with title $name")

    /* TODO possibilities */
    private fun SpotifyApi.findSongByName(name: String) =
            api.searchTracks(name).build().get().items.find { it.name == name }
            ?: throw Exception("Unable to find song with title $name")

    override fun createPlaylist(name: String) {
        TODO("not implemented")
    }

    /* Requires manual http request */
    override fun removePlaylist(name: String) {
        TODO("not supported")
    }

    override fun updatePlaylist(name: String, songs: List<Song>) {
        TODO("not supported")
        /*
        api.findPlaylist(title).let {
            api.addTracksToPlaylist(userID, it.id, songs.map { api.findSongByName(it.title).title })
        }
        */
    }

    override fun findPlaylist(name: String) =
            api.findPlaylist(name).let {
                Playlist(it.name!!, listOf())
            }

    override fun findSong(name: String): Song {
        TODO("not implemented")
    }

    override fun addSong(name: String) {
        api.findSongByName(name).let {
            api.addToMySavedTracks(listOf(it.name)).build().get()
        }
    }

    override fun removeSong(name: String) {
        api.mySavedTracks.build().get().items.find { it.track.name == name }?.let {
            api.removeFromMySavedTracks(listOf(it.track.id))
        } ?: throw Exception("Unable to remove song with title $name, as it does not exist")
    }

    companion object {
        fun getAuthenticationURL(clientId: String, clientSecret: String, redirect: String): String {
            val api = SpotifyApi.builder()
                    .clientId(clientId)
                    .clientSecret(clientSecret)
                    .redirectURI(redirect)
                    .build()

            /* do user authentication */
            val scopes = listOf(
                    "playlist-read-private",
                    "playlist-read-collaborative",
                    "playlist-modify-public",
                    "playlist-modify-private",
                    "ugc-image-upload",
                    "user-library-read",
                    "user-library-modify",
                    "user-follow-modify"
            )
            /* prevent cross site forgeries with a state string */
            val state = UUID.randomUUID().toString()
            /* TODO use this to authenticate */
            return api.createAuthorizeURL(scopes, state)
        }

        fun privilegedInstance(api: SpotifyApi, name: String): SpotifyService {
            getUser(name)?.spotifyAuthCode?.let {
                println("CODE: $it")
                val refresh = api.authorizationCodeGrant(it).build().get()
                    ?: throw Exception("unable to get refresh code")
                api.setAccessToken(refresh.accessToken)
                api.setRefreshToken(refresh.refreshToken)
            } ?: throw Exception("$name is an unauthenticated user")
            return SpotifyService(name, api)
        }
    }
}