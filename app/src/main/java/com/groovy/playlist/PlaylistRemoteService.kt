package com.groovy.playlist

import com.groovy.playlist.dto.PlaylistRaw
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@ViewModelScoped
class PlaylistRemoteService @Inject constructor(
    private val playlistApiService: PlaylistAPIService
) {
    suspend fun fetchPlaylists(): Flow<Result<List<PlaylistRaw>>> {
        return flow {
            emit(Result.success(playlistApiService.fetchPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}
