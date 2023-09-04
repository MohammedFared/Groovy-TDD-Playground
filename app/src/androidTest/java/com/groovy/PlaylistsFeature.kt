package com.groovy

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.groovy.utils.RecyclerViewAssertions.assertDisplayedAtPosition
import com.groovy.utils.RecyclerViewAssertions.assertDrawableDisplayedAtPosition

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PlaylistsFeature {

    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlists")
    }

    @Test
    fun displayPlaylistsList() {
        Thread.sleep(4000)

        assertRecyclerViewItemCount(R.id.rvPlaylist, 10)
        assertDisplayedAtPosition(R.id.rvPlaylist, 0, R.id.txtTitle, "Hard Rock Cafe")
        assertDisplayedAtPosition(R.id.rvPlaylist, 0, R.id.txtCategory, "rock")
        assertDrawableDisplayedAtPosition(R.id.rvPlaylist, 0, R.id.imgPlaylist, R.mipmap.playlist)
    }
}