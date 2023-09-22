package com.groovy.playlist

import com.groovy.playlist.dto.PlaylistRaw
import retrofit2.http.GET

interface PlaylistAPIService {
    @GET("playlists")
    suspend fun fetchPlaylists(): List<PlaylistRaw>
}
