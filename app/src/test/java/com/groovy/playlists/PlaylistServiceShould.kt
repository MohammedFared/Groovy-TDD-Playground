package com.groovy.playlists

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import com.groovy.playlist.PlaylistAPIService
import com.groovy.playlist.Playlist
import com.groovy.playlist.PlaylistService
import com.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistServiceShould : BaseUnitTest() {

    private val exception = RuntimeException("Something went wrong")
    private val playlists = mock<List<Playlist>>()
    private val playlistApiService: PlaylistAPIService = mock()

    @Test
    fun getPlaylists() = runTest {
        PlaylistService(playlistApiService).fetchPlaylists().first()

        verify(playlistApiService).fetchPlaylists()
    }

    @Test
    fun fetchPlaylists() = runTest {
        val service = mockSuccessfulCase()

        assertEquals(Result.success(playlists), service.fetchPlaylists().first())
    }

    @Test
    fun propagateErrors() = runTest {
        val service = mockFailedCase()

        assertEquals(exception.message, service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private suspend fun mockSuccessfulCase(): PlaylistService {
        `when`(playlistApiService.fetchPlaylists()).thenReturn(playlists)
        return PlaylistService(playlistApiService)
    }

    private suspend fun mockFailedCase(): PlaylistService {
        `when`(playlistApiService.fetchPlaylists())
            .thenThrow(RuntimeException("non user friendly message"))
        return PlaylistService(playlistApiService)
    }
}