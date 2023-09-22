package com.groovy.playlist

import com.groovy.R
import com.groovy.playlist.dto.Playlist
import com.groovy.playlist.dto.PlaylistRaw
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlaylistRaw>, List<Playlist>> {

    override fun invoke(playlistsRaw: List<PlaylistRaw>): List<Playlist> {
        return playlistsRaw.map {
            Playlist(it).apply {
                playlistArt =
                    if (playlistCategory.equals("rock", ignoreCase = true)) R.mipmap.rock
                    else R.mipmap.playlist
            }
        }
    }
}
