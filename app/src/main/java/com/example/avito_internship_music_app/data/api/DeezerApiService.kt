package com.example.avito_internship_music_app.data.api

import com.example.avito_internship_music_app.data.model.ChartResponse
import com.example.avito_internship_music_app.data.model.Track
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    @GET("chart")
    suspend fun getChartTracks(): ChartResponse

    @GET("search")
    suspend fun searchTracks(@Query("q") query: String): ChartResponse

    @GET("track/{id}")
    suspend fun getTrackById(@Path("id") id: Long): Track
}