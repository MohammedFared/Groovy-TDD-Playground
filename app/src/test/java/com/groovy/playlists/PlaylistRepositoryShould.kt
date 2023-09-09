package com.groovy.playlists

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import com.groovy.playlist.dto.Playlist
import com.groovy.playlist.PlaylistRepository
import com.groovy.playlist.PlaylistRemoteService
import com.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistRepositoryShould : BaseUnitTest() {

    private val exception = RuntimeException("Something went wrong")
    private val service: PlaylistRemoteService = mock()
    private val playlists = mock<List<Playlist>>()

    @Test
    fun getPlaylistsFromService() = runTest {
        PlaylistRepository(service).also {
            it.getPlaylists()
        }

        verify(service, times(1)).fetchPlaylists()
    }

    @Test
    fun emitPlaylistsFromService() = runTest {
        val repository = mockSuccessfulCase()

        assertEquals(Result.success(playlists), repository.getPlaylists().first())
    }

    @Test
    fun propagateErrors() = runTest {
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        val repository = PlaylistRepository(service)
        return repository
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlists))
            }
        )

        return PlaylistRepository(service)
    }
}
