package com.example.avito_internship_music_app.viewmodel

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
            runCatching { storage.getChartTracks().tracks }
                .onSuccess { _tracks.value = it }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            runCatching { storage.searchTracks(query).tracks }
                .onSuccess { _tracks.value = it }
        }
    }
}