package com.example.avito_internship_music_app.storage

//import com.example.avito_internship_music_app.data.api.RetrofitInstance
import com.example.avito_internship_music_app.data.model.Track

class MusicStorage {
    private val api = RetrofitInstance.api

    suspend fun getChartTracks() = api.getChartTracks()
    suspend fun searchTracks(query: String) = api.searchTracks(query)
    suspend fun getTrackById(id: Long): Track {
        return api.getTrackById(id)
    }

}