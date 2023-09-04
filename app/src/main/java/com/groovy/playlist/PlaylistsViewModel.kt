package com.groovy.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaylistsViewModel(
    private val repository: PlaylistRepository
) : ViewModel() {

    val playlists = liveData {
        emitSource(repository.getPlaylists().asLiveData())
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://ngrok-free.app/groovy/")
                    .client(OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val playlistApiService = retrofit.create(PlaylistAPIService::class.java)

                val service = PlaylistService(playlistApiService)
                val repository = PlaylistRepository(service)
                PlaylistsViewModel(repository)
            }
        }
    }
}