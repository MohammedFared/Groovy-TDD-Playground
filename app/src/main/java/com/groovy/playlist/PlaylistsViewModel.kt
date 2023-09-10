package com.groovy.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val repository: PlaylistRepository
) : ViewModel() {

    val showLoader = MutableLiveData<Boolean>()

    val playlists = liveData {
        showLoader.postValue(true)

        emitSource(
            repository.getPlaylists()
                .onCompletion {
                    showLoader.postValue(false)
                }
                .asLiveData()
        )
    }
}