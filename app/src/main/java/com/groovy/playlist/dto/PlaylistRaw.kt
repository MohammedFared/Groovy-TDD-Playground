package com.groovy.playlist.dto

import com.google.gson.annotations.SerializedName

data class PlaylistRaw (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val playlistTitle: String,
    @SerializedName("category")
    val playlistCategory: String,
)
