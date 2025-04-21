package com.example.avito_internship_music_app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ChartResponse(
    val tracks: TrackDataWrapper
)
@Serializable
data class TrackDataWrapper(
    val data: List<Track>
)