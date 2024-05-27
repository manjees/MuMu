package com.manjee.core.datastore.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class MyArtistData(
    val id: Long,
    val name: String,
)

fun MyArtistData.toJsonString(): String = Json.encodeToString(MyArtistData.serializer(), this)

fun String.toMyArtist(): MyArtistData = Json.decodeFromString(MyArtistData.serializer(), this)
