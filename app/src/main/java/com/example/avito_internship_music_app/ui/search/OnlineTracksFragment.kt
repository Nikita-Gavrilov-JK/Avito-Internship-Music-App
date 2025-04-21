package com.example.avito_internship_music_app.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avito_internship_music_app.R
import com.example.avito_internship_music_app.databinding.FragmentOnlineTracksBinding
import com.example.avito_internship_music_app.viewmodel.TracksViewModel
import kotlinx.coroutines.flow.collectLatest

class OnlineTracksFragment : Fragment(R.layout.fragment_online_tracks) {
    private val viewModel: TracksViewModel by viewModels()
    private lateinit var adapter: TracksAdapter
    private var _binding: FragmentOnlineTracksBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOnlineTracksBinding.bind(view)
        adapter = TracksAdapter { track ->
            val action = OnlineTracksFragmentDirections
                .actionOnlineTracksFragmentToPlayerFragment(track.id)
            findNavController().navigate(action)
        }

        binding.tracksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tracksRecyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            viewModel.tracks.collectLatest { adapter.submitList(it) }
        }

        binding.searchInput.addTextChangedListener {
            val query = it.toString()
            if (query.isNotBlank()) viewModel.search(query)
            else viewModel.loadChart()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}