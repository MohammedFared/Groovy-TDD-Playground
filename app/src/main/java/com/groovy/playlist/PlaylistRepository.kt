package com.groovy.playlist

import com.groovy.playlist.dto.Playlist
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class PlaylistRepository @Inject constructor(
    private val service: PlaylistRemoteService,
    private val mapper: PlaylistMapper
) {
    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> =
        service.fetchPlaylists().map {
            if (it.isSuccess)
                Result.success(mapper(it.getOrNull()!!))
            else
                Result.failure(it.exceptionOrNull()!!)
        }
}
