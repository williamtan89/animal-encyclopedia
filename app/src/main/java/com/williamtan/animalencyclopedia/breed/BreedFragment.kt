package com.williamtan.animalencyclopedia.breed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.adapter.BreedAdapter
import com.williamtan.animalencyclopedia.databinding.FragmentBreedBinding
import com.williamtan.animalencyclopedia.view.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreedFragment : Fragment() {
    private val viewModel: BreedViewModel by viewModels()
    private lateinit var binding: FragmentBreedBinding
    private lateinit var adapter: BreedAdapter

    val args: BreedFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbBreed.setNavigationIcon(R.drawable.ic_back_24)
        binding.tbBreed.setNavigationOnClickListener {
            view.findNavController().popBackStack()
        }

        adapter = BreedAdapter()
        binding.rvBreed.adapter = adapter
        binding.rvBreed.addItemDecoration(GridItemDecoration(16, 2))

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is BreedViewModel.ScreenState.Empty -> {
                            binding.layoutEmptyState.isVisible = true
                            binding.layoutErrorState.isVisible = false
                        }

                        is BreedViewModel.ScreenState.Error -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = true
                        }

                        is BreedViewModel.ScreenState.Success -> {
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
                    adapter.submitList(it)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.loadBreeds(args.animalType)
        }
    }
}