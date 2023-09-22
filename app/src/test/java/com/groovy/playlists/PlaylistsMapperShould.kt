package com.groovy.playlists

import com.groovy.R
import com.groovy.playlist.PlaylistMapper
import com.groovy.playlist.dto.Playlist
import com.groovy.playlist.dto.PlaylistRaw
import com.groovy.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.Mockito.mock

class PlaylistsMapperShould : BaseUnitTest() {
    val playlistRaw = PlaylistRaw(
        "1", "da Name", "da Category"
    )

    val playlistRawRockCategory = PlaylistRaw(
        "1", "da Name", "rock"
    )

    @Test
    fun mapPlaylistRawToPlaylist() {
        val playlists = PlaylistMapper().invoke(listOf(playlistRaw))

        assertThat(playlists.first(), instanceOf(Playlist::class.java))
    }

    @Test
    fun keepTheID() {
        val playlist = PlaylistMapper().invoke(listOf(playlistRaw))[0]

        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepTheName() {
        val playlist = PlaylistMapper().invoke(listOf(playlistRaw))[0]

        assertEquals(playlistRaw.playlistTitle, playlist.playlistTitle)
    }

    @Test
    fun keepTheCategory() {
        val playlist = PlaylistMapper().invoke(listOf(playlistRaw))[0]

        assertEquals(playlistRaw.playlistCategory, playlist.playlistCategory)
    }

    @Test
    fun updateTheArtBasedOnTheCategory() {
        val playlistRock = PlaylistMapper().invoke(listOf(playlistRawRockCategory))[0]
        val playlistDefault = PlaylistMapper().invoke(listOf(playlistRaw))[0]

        assertEquals(R.mipmap.rock, playlistRock.playlistArt)
        assertEquals(R.mipmap.playlist, playlistDefault.playlistArt)
    }
}