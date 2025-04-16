package com.example.avito_internship_music_app.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChartResponse(
    @SerialName("")
    val tracks: List<Track>
)
