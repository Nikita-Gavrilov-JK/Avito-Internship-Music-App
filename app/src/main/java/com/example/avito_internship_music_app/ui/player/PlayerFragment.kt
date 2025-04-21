package com.example.avito_internship_music_app.ui.player
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
//import com.example.avito_internship_music_app.ARG_PARAM1
//import com.example.avito_internship_music_app.ARG_PARAM2
import com.example.avito_internship_music_app.R
import com.example.avito_internship_music_app.data.model.Track
import com.example.avito_internship_music_app.databinding.FragmentPlayerBinding
import com.example.avito_internship_music_app.storage.MusicStorage
import com.example.avito_internship_music_app.viewmodel.PlayerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class PlayerFragment : Fragment() {
    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<PlayerFragmentArgs>()
    private val trackId: Long by lazy { args.trackId }
    private val viewModel: PlayerViewModel by viewModels()

    private var mediaPlayer: MediaPlayer? = null
    private var updateJob: Job? = null
    private var isPlaying = false
        //    private lateinit var track: Track


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.loadTrackById(args.trackId)

        lifecycleScope.launchWhenStarted {
            viewModel.track.collect { track ->
                if (track != null) {
                    binding.trackTitle.text = track.title
                    binding.artistAndAlbum.text = "${track.artist.name}"

                    Glide.with(binding.imageCover)
                        .load(track.album.cover_medium)
                        .placeholder(R.drawable.ic_music_placeholder)
                        .into(binding.imageCover)

                    mediaPlayer?.release()
                    mediaPlayer = MediaPlayer().apply {
                        setDataSource(track.preview)
                        prepare()
                        setOnPreparedListener {
                            binding.progressSeekBar.max = duration
                            binding.totalTime.text = formatTime(duration)
                            start()
                            this@PlayerFragment.isPlaying = true
                            binding.btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)
                            startUpdatingSeekBar()
                        }
                        setOnCompletionListener {
                            this@PlayerFragment.isPlaying = false
                            binding.btnPlayPause.setImageResource(android.R.drawable.ic_media_play)
                            binding.progressSeekBar.progress = 0
                        }
                    }

                    binding.btnPlayPause.setOnClickListener {
                        mediaPlayer?.let {
                            if (it.isPlaying) {
                                it.pause()
                                isPlaying = false
                                binding.btnPlayPause.setImageResource(android.R.drawable.ic_media_play)
                            } else {
                                it.start()
                                isPlaying = true
                                binding.btnPlayPause.setImageResource(android.R.drawable.ic_media_pause)
                            }
                        }
                    }

                    binding.progressSeekBar.setOnSeekBarChangeListener(object :
                        SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            if (fromUser) {
                                mediaPlayer?.seekTo(progress)
                                binding.currentTime.text = formatTime(progress)
                            }
                        }

                        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                    })
                }
            }
        }
    }

    private fun startUpdatingSeekBar() {
        updateJob = viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            while (isActive && mediaPlayer != null) {
                binding.progressSeekBar.progress = mediaPlayer!!.currentPosition
                binding.currentTime.text = formatTime(mediaPlayer!!.currentPosition)
                delay(500)
            }
        }
    }

    private fun formatTime(milliseconds: Int): String {
        val formatter = SimpleDateFormat("m:ss", Locale.getDefault())
        return formatter.format(Date(milliseconds.toLong()))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        updateJob?.cancel()
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }
}