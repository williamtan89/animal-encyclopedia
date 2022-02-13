package com.williamtan.animalencyclopedia.breeddetail

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
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.databinding.FragmentBreedDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BreedDetailFragment : Fragment() {
    private val viewModel: BreedDetailViewModel by viewModels()

    private lateinit var binding: FragmentBreedDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBreedDetailBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbBreed.apply {
            setNavigationIcon(R.drawable.ic_back_24)
            setNavigationOnClickListener {
                view.findNavController().popBackStack()
            }
        }

        // start collecting uistate flow
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    when (it) {
                        is BreedDetailViewModel.ScreenState.Empty -> {
                            binding.layoutEmptyState.isVisible = true
                            binding.layoutErrorState.isVisible = false
                        }

                        is BreedDetailViewModel.ScreenState.Error -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = true
                        }

                        is BreedDetailViewModel.ScreenState.Success -> {
                            binding.layoutEmptyState.isVisible = false
                            binding.layoutErrorState.isVisible = false

                            with(it.breed) {
                                binding.tvBreedName.text = this.toString()
                            }
                        }
                    }
                }
            }
        }
    }
}