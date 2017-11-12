package com.ackdevelopment.songport

import com.ackdevelopment.songport.models.User
import org.litote.kmongo.KMongo
import org.litote.kmongo.findOneById
import org.litote.kmongo.getCollection

val mongo = KMongo.createClient()
val database = mongo.getDatabase("songport")!!

fun getUsers() = database.getCollection<User>("users")

fun getUser(id: String) = getUsers().findOneById(id)
