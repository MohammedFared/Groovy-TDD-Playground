package com.groovy.playlist

import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PlaylistRepository @Inject constructor(
    private val service: PlaylistRemoteService
) {
    suspend fun getPlaylists() = service.fetchPlaylists()
}
