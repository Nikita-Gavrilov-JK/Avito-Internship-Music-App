package com.example.avito_internship_music_app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.avito_internship_music_app.data.model.Track
import com.example.avito_internship_music_app.storage.MusicStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    private val _track = MutableStateFlow<Track?>(null)
    val track: StateFlow<Track?> = _track

    fun loadTrackById(trackId: Long) {
        viewModelScope.launch {
            runCatching {
                MusicStorage().getTrackById(trackId)
            }.onSuccess {
                _track.value = it
            }.onFailure {
                // Обработка ошибки  it.printStackTrace()
            }
        }
    }
}

