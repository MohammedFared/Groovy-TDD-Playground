package petros.efthymiou.groovy.playlist

import retrofit2.http.GET

interface PlaylistAPIService {
    @GET("playlists")
    suspend fun fetchPlaylists(): List<Playlist>
}
