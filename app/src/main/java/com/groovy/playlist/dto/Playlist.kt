package com.groovy.playlist.dto

import com.google.gson.annotations.SerializedName
import com.groovy.R

data class Playlist(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val playlistTitle: String,
    @SerializedName("category")
    val playlistCategory: String,
    val playlistArt: Int = R.mipmap.playlist
)