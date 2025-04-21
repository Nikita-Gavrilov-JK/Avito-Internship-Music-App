package com.example.avito_internship_music_app.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avito_internship_music_app.data.model.Track
import com.example.avito_internship_music_app.storage.MusicStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TracksViewModel : ViewModel(){
    private val storage = MusicStorage()

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks

    init {
        loadChart()
    }

    fun loadChart() {
        viewModelScope.launch {
            try {
                val chartResponse = storage.getChartTracks()
                Log.d("TracksViewModel", "Загружено ${chartResponse.tracks.data.size} треков")
                _tracks.value = chartResponse.tracks.data
            } catch (e: Exception) {
                Log.e("TracksViewModel", "Ошибка загрузки чартов: ${e.message}")
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            try {
                val searchResponse = storage.searchTracks(query)
                _tracks.value = searchResponse.data
            } catch (e: Exception) {
                Log.e("TracksViewModel", "Ошибка поиска: ${e.message}")
            }
        }
    }
}