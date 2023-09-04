package petros.efthymiou.groovy.playlist

import kotlinx.coroutines.flow.Flow

class PlaylistRepository(
    private val service: PlaylistService
) {
    suspend fun getPlaylists() = service.fetchPlaylists()

}
