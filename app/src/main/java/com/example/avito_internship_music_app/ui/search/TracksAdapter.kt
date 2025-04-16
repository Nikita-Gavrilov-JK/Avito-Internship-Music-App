package com.example.avito_internship_music_app.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.avito_internship_music_app.R
import com.example.avito_internship_music_app.data.model.Track

class TracksAdapter( private val onClick: (Track) -> Unit
) : ListAdapter<Track, TracksAdapter.TrackViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track, parent, false)
        return TrackViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TrackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.trackTitle)
        private val artist: TextView = view.findViewById(R.id.trackArtist)
        private val image: ImageView = view.findViewById(R.id.imageCover)

        fun bind(track: Track) {
            title.text = track.title
            artist.text = track.artist.name
            Glide.with(image)
                .load(track.album.cover)
                .placeholder(R.drawable.ic_music_placeholder)
                .into(image)

            itemView.setOnClickListener { onClick(track) }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean = oldItem == newItem
    }
}