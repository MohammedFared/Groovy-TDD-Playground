package com.groovy.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.groovy.R
import com.groovy.databinding.ItemPlaylistBinding

class PlayListsAdapter(
    private val values: List<Playlist>
) : RecyclerView.Adapter<PlayListsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPlaylistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values.get(position)
        holder.title.text = item.playlistTitle
        holder.category.text = item.playlistCategory
        holder.playlistArt.setImageResource(R.mipmap.playlist)
    }

    override fun getItemCount(): Int = values.size

     inner class ViewHolder(binding: ItemPlaylistBinding) : RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.txtTitle
        val category: TextView = binding.txtCategory
        val playlistArt: ImageView = binding.imgPlaylist
    }
}