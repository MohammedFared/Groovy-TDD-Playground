package petros.efthymiou.groovy.playlists

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.notification.Failure
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import petros.efthymiou.groovy.playlist.Playlist
import petros.efthymiou.groovy.playlist.PlaylistRepository
import petros.efthymiou.groovy.playlist.PlaylistsViewModel
import petros.efthymiou.groovy.utils.BaseUnitTest
import petros.efthymiou.groovy.utils.getValueForTest
import java.lang.RuntimeException

class PlaylistsViewModelShould : BaseUnitTest() {

    private val repository: PlaylistRepository = mock()
    private val playlists = mock<List<Playlist>>()
    private val expectedList = Result.success(playlists)
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistsFromRepository() = runTest {
        val viewModel = mockRepositoryGetPlaylists()

        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }

    @Test
    fun emitsPlaylistsFromRepository() {
        val viewModel = mockRepositoryGetPlaylists()

        assertEquals(expectedList, viewModel.playlists.getValueForTest())
    }

    @Test
    fun emitErrorWhenReceiveError() {
        runBlocking {
            `when`(repository.getPlaylists()).thenReturn(
                flow {
                    emit(Result.failure<List<Playlist>>(exception))
                }
            )
        }

        val viewModel = PlaylistsViewModel(repository)
        assertEquals(exception, viewModel.playlists.getValueForTest()?.exceptionOrNull())
    }

    private fun mockRepositoryGetPlaylists(): PlaylistsViewModel = runBlocking {
        `when`(repository.getPlaylists()).thenReturn(
            flow { emit(expectedList) }
        )
        return@runBlocking PlaylistsViewModel(repository)
    }
}
