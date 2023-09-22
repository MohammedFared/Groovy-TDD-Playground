package com.groovy.playlists

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import com.groovy.playlist.PlaylistAPIService
import com.groovy.playlist.PlaylistRemoteService
import com.groovy.playlist.dto.PlaylistRaw
import com.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistRemoteServiceShould : BaseUnitTest() {

    private val exception = RuntimeException("Something went wrong")
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val playlistApiService: PlaylistAPIService = mock()

    @Test
    fun getPlaylists() = runTest {
        PlaylistRemoteService(playlistApiService).fetchPlaylists().first()

        verify(playlistApiService).fetchPlaylists()
    }

    @Test
    fun fetchPlaylists() = runTest {
        val service = mockSuccessfulCase()

        assertEquals(Result.success(playlistsRaw), service.fetchPlaylists().first())
    }

    @Test
    fun propagateErrors() = runTest {
        val service = mockFailedCase()

        assertEquals(exception.message, service.fetchPlaylists().first().exceptionOrNull()?.message)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRemoteService {
        `when`(playlistApiService.fetchPlaylists()).thenReturn(playlistsRaw)
        return PlaylistRemoteService(playlistApiService)
    }

    private suspend fun mockFailedCase(): PlaylistRemoteService {
        `when`(playlistApiService.fetchPlaylists())
            .thenThrow(RuntimeException("non user friendly message"))
        return PlaylistRemoteService(playlistApiService)
    }
}