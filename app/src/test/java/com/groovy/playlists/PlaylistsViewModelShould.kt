package com.groovy.playlists

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import com.groovy.playlist.dto.Playlist
import com.groovy.playlist.PlaylistRepository
import com.groovy.playlist.PlaylistsViewModel
import com.groovy.utils.BaseUnitTest
import com.groovy.utils.captureValues
import com.groovy.utils.getValueForTest
import java.lang.RuntimeException

class PlaylistsViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expectedList = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistsFromRepository() {
        val viewModel = mockSuccessfulCase()

        assertEquals(expectedList, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() {
        val viewModel = mockFailedCase()
        assertEquals(exception, viewModel.playlists.getValueForTest()?.exceptionOrNull())
    }

    @Test
    fun showLoaderWhileLoading() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.showLoader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(true, values[0])
        }
    }

    @Test
    fun hideLoaderWhenFinishedLoading() = runTest {
        val viewModel = mockSuccessfulCase()

        viewModel.showLoader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    @Test
    fun hideLoaderAfterError() = runTest {
        val viewModel = mockFailedCase()

        viewModel.showLoader.captureValues {
            viewModel.playlists.getValueForTest()

            assertEquals(false, values.last())
        }
    }

    private fun mockSuccessfulCase(): PlaylistsViewModel = runBlocking {
        `when`(repository.getPlaylists()).thenReturn(
            flow { emit(expectedList) }
        )
        return@runBlocking PlaylistsViewModel(repository)
    }

    private fun mockFailedCase(): PlaylistsViewModel = runBlocking {
        `when`(repository.getPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )
        return@runBlocking PlaylistsViewModel(repository)
    }
}
