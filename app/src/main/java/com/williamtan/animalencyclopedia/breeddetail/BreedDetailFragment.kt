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
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.williamtan.animalencyclopedia.R
import com.williamtan.animalencyclopedia.databinding.FragmentBreedDetailBinding
import com.williamtan.common.entity.BreedEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BreedDetailFragment : Fragment() {
    companion object {
        private const val MAXIMUM_ENERGY_LEVEL = 5.0f
    }

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

                            updateUi(it.breed)
                        }
                    }
                }
            }
        }
    }

    private fun updateUi(breed: BreedEntity) {
        binding.tvBreedId.text = resources.getString(R.string.breed_detail_id, breed.id.uppercase())
        binding.tvBreedName.text = breed.name

        binding.tvBreedDesc.text = breed.description

        breed.temperament.forEach { t ->
            val newChip = Chip(context).apply {
                text = t
                setEnsureMinTouchTargetSize(false)
                isClickable = false
            }

            binding.cgTemperament.addView(newChip)
        }

        binding.tvEnergyLevel.text = resources.getString(
            R.string.breed_detail_energy_level,
            breed.energyLevel
        )

        binding.pbEnergyLevel.progress = (breed.energyLevel / MAXIMUM_ENERGY_LEVEL * 100).toInt()

        Glide.with(this)
            .load(breed.imageUrl)
            .into(binding.ivToolbar)
    }
}