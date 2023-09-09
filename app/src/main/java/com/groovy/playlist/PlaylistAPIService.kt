package com.groovy.playlist

import com.groovy.playlist.dto.Playlist
import retrofit2.http.GET

interface PlaylistAPIService {
    @GET("playlists")
    suspend fun fetchPlaylists(): List<Playlist>
}
