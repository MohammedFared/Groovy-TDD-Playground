package com.groovy.playlists

import com.groovy.playlist.PlaylistMapper
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
import com.groovy.playlist.dto.PlaylistRaw
import com.groovy.utils.BaseUnitTest
import java.lang.RuntimeException

class PlaylistRepositoryShould : BaseUnitTest() {

    private val exception = RuntimeException("Something went wrong")
    private val service: PlaylistRemoteService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()

    @Test
    fun getPlaylistsFromService() = runTest {
        mockSuccessfulCase().also {
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

    @Test
    fun delegateBusinessLogicToMapper() = runTest {
        val repository = mockSuccessfulCase()

        //TODO try removing the first() call
        repository.getPlaylists().first()

        verify(mapper).invoke(playlistsRaw)
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure(exception))
            }
        )

        return PlaylistRepository(service, mapper)
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        `when`(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlistsRaw))
            }
        )

        `when`(mapper.invoke(playlistsRaw)).thenReturn(playlists)
        return PlaylistRepository(service, mapper)
    }
}
