package com.groovy.playlist

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class PlaylistsModule {

    @Provides
    @ViewModelScoped
    fun playlistApi(retrofit: Retrofit): PlaylistAPIService = retrofit.create(PlaylistAPIService::class.java)

}