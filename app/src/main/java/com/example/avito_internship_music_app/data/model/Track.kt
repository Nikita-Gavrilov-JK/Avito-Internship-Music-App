package com.example.avito_internship_music_app.data.model
import kotlinx.serialization.Serializable
@Serializable
data class Track(
    val id: Long,
    val title: String,
    val preview: String,
    val artist: Artist,
    val album: Album
)

@Serializable
data class Artist(val name: String)

@Serializable
data class Album(val cover: String)

