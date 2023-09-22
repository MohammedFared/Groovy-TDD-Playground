package com.groovy.playlist.dto

import com.google.gson.annotations.SerializedName
import com.groovy.R

data class Playlist(
    var id: String,
    var playlistTitle: String,
    var playlistCategory: String,
    var playlistArt: Int = R.mipmap.playlist
) {
    constructor(playlistRaw: PlaylistRaw) : this(
        playlistRaw.id,
        playlistRaw.playlistTitle,
        playlistRaw.playlistCategory,
    )
}