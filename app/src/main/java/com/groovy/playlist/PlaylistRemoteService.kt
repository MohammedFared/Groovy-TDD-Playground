package com.groovy.playlist

import com.groovy.playlist.dto.Playlist
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

@ViewModelScoped
class PlaylistRemoteService @Inject constructor(
    private val playlistApiService: PlaylistAPIService
) {
    suspend fun fetchPlaylists(): Flow<Result<List<Playlist>>> {
        return flow {
            emit(Result.success(playlistApiService.fetchPlaylists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}
