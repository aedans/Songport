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

fun putUserIfMissing(user: User) = getUser(user._id) ?: putUser(user)

fun getPlaylists() = database.getCollection<Playlist>("playlists")

fun getPlaylist(id: String) = getPlaylists().findOneById(id)

fun getSongs() = database.getCollection<Song>("songs")

fun getSong(id: String) = getSongs().findOneById(id)
