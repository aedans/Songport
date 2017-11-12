package com.ackdevelopment.songport

import com.ackdevelopment.songport.models.Playlist
import com.ackdevelopment.songport.models.Song
import com.ackdevelopment.songport.models.User
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection

val mongo = KMongo.createClient()
val database = mongo.getDatabase("songport")!!

fun getUsers() = database.getCollection<User>("users")

fun getUser(id: String) = getUsers().findOneById(id)

fun putUser(user: User) = getUsers().insertOne(user)

fun getPlaylists() = database.getCollection<Playlist>("playlists")

fun getPlaylist(id: String) = getPlaylists().findOneById(id)

fun putPlaylist(playlist: Playlist) = getPlaylists().insertOne(playlist)

fun putPlaylistIfMissing(playlist: Playlist) = getPlaylist(playlist._id) ?: putPlaylist(playlist)

fun getSongs() = database.getCollection<Song>("songs")

fun getSong(id: String) = getSongs().findOneById(id)

fun putSong(song: Song) = getSongs().insertOne(song)

fun putSongIfMissing(song: Song) = getSong(song._id) ?: putSong(song)
