package com.example.footballstandings.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.footballstandings.R
import com.example.footballstandings.databinding.FragmentLeaguesBinding
import com.example.footballstandings.ui.adapters.CompetitionsAdapter
import com.example.footballstandings.ui.viewmodels.FsUiState
import com.example.footballstandings.ui.viewmodels.FsViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LeaguesFragment : Fragment() {
    private lateinit var binding: FragmentLeaguesBinding
    private val viewModel: FsViewModel by hiltNavGraphViewModels(R.id.fs_nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLeaguesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val competitionsAdapter = CompetitionsAdapter()
        binding.competitionsRecyclerView.adapter = competitionsAdapter
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    FsUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.competitionsRecyclerView.visibility = View.GONE
                    }
                    is FsUiState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.competitionsRecyclerView.visibility = View.GONE

                        context?.let {
                            MaterialAlertDialogBuilder(it)
                                .setTitle("Failed to load leagues")
                                .setMessage(state.message)
                                .setPositiveButton("Accept") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }
                    is FsUiState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.competitionsRecyclerView.visibility = View.VISIBLE
                        competitionsAdapter.submitList(state.leagues)
                    }
                }
            }
        }
    }
}