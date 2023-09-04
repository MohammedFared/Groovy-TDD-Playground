package petros.efthymiou.groovy.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import kotlinx.coroutines.channels.ReceiveChannel
import okhttp3.OkHttpClient
import petros.efthymiou.groovy.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A fragment representing a list of Items.
 */
class PlayListsFragment : Fragment() {

    private val viewModel: PlaylistsViewModel by viewModels { PlaylistsViewModel.Factory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_play_lists_list, container, false)

        viewModel.playlists.observe(viewLifecycleOwner) {
            // Set the adapter
            val playlistList = it.getOrNull()
            if (playlistList.isNullOrEmpty()) {
                Toast.makeText(context, "Empty list", Toast.LENGTH_SHORT).show()
                return@observe
            }

            (view as RecyclerView).layoutManager = LinearLayoutManager(context)
            view.adapter = PlayListsAdapter(playlistList)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayListsFragment()
    }
}