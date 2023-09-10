package com.groovy.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.groovy.R
import com.groovy.playlist.dto.Playlist
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class PlayListsFragment : Fragment() {

    private val viewModel: PlaylistsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_play_lists_list, container, false)

        initObservers(view)
        return view
    }

    private fun initObservers(view: View?) {
        viewModel.showLoader.observe(viewLifecycleOwner) {
            val loader = view?.findViewById<ProgressBar>(R.id.loader) ?: return@observe
            loader.isVisible = it
        }

        viewModel.playlists.observe(viewLifecycleOwner) {
            setupPlaylists(it, view)
        }
    }

    private fun setupPlaylists(
        playlists: Result<List<Playlist>>,
        view: View?
    ) {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.rvPlaylist) ?: return
        val playlistList = playlists.getOrNull()
        if (playlistList.isNullOrEmpty()) {
            Toast.makeText(context, "Empty list", Toast.LENGTH_SHORT).show()
            return
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = PlayListsAdapter(playlistList)
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayListsFragment()
    }
}