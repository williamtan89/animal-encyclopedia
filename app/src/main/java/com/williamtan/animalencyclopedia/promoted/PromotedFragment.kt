package com.williamtan.animalencyclopedia.promoted

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
import com.williamtan.animalencyclopedia.adapter.PromotedBreedsAdapter
import com.williamtan.animalencyclopedia.databinding.FragmentPromotedBinding
import com.williamtan.animalencyclopedia.home.HomeFragmentDirections
import com.williamtan.common.enumtype.AnimalType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PromotedFragment : Fragment() {
    private val viewModel: PromotedViewModel by viewModels()
    private lateinit var binding: FragmentPromotedBinding
    private lateinit var adapter: PromotedBreedsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPromotedBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PromotedBreedsAdapter(onAnimalTypeClick, onPromotedBreedClick)
        binding.rvPromoted.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is PromotedViewModel.ScreenState.Empty -> {
                            binding.layoutEmptyState.isVisible = true
                            binding.layoutErrorState.isVisible = false
                        }

                        is PromotedViewModel.ScreenState.Error -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = true
                        }

                        is PromotedViewModel.ScreenState.Success -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = false
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataMap.collect {
                    adapter.submitList(it.values.toList())
                }
            }
        }
    }

    private val onPromotedBreedClick: (AnimalType, String) -> Unit = { animalType, breedId ->
        val action = HomeFragmentDirections.homeToBreedDetailAction(animalType, breedId)
        view?.findNavController()?.navigate(action)
    }

    private val onAnimalTypeClick: (AnimalType) -> Unit = { animalType ->
        val action = HomeFragmentDirections.homeToBreedAction(animalType)
        view?.findNavController()?.navigate(action)
    }
}