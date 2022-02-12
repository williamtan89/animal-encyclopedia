package com.williamtan.animalencyclopedia.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.williamtan.animalencyclopedia.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HomeAdapter()
        binding.rvHome.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is HomeViewModel.HomeState.Empty -> {
                            binding.layoutEmptyState.isVisible = true
                            binding.layoutErrorState.isVisible = false
                        }

                        is HomeViewModel.HomeState.Error -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = true
                        }

                        is HomeViewModel.HomeState.Success -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = false
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataMap.collect {
                    adapter.submitList(it.values.toList()) {
                        binding.rvHome.post {
                            binding.rvHome.invalidateItemDecorations()
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.loadAnimalType()
        }
    }
}